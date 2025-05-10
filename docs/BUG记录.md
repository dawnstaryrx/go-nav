bug2（严重，优先处理）：
复现场景：
以新增的普通用户guest(role=2),后台管理新增一个应用，权限为私有或者登陆后可看时，通过ip/nav/guest访问时，无法看到应用，只有当应用权限为公开时，才能看到
导致问题：
无法有效管理



```xml
<select id="getAppVOsByUserId" resultType="com.dawn.gonav.model.vo.AppVO">
    select
        app.id,
        app.name,
        app.url,
        app.icon_url,
        app.category_id,
        category.name as category_name,
        app.description,
        app.weight,
        app.click_count,
        app.status
    from app
    left join category on app.category_id = category.id
    <where>
        <if test="userId != null">
            app.create_user = #{userId}
        </if>
        <if test="status != null">
            and app.status &lt;= #{status}
        </if>
        <if test="categoryId != null">
            and app.category_id = #{categoryId}
                or app.category_id in
                (select id from category where (parent_id = #{categoryId} or id = #{categoryId}))
        </if>
        <!-- bug -->
        and category.status = 1
    </where>
    order by app.weight desc
</select>
```

bug: 首页不能展示无分类应用

`and category.status = 1` -> `and (category.status = 1 or category.status is null)`

bug: 应用在分类展示下的权限问题

```
<if test="categoryId != null">
    and app.category_id = #{categoryId}
        or app.category_id in
        (select id from category where (parent_id = #{categoryId} or id = #{categoryId}))
</if>
```

```
<if test="categoryId != null">
    and (
        app.category_id = #{categoryId}
        or app.category_id in (
            select id from category
            where parent_id = #{categoryId} or id = #{categoryId}
        )
    )
</if>
```

