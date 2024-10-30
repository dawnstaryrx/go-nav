<template>
  <div>
    <table class="table text-center">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col" @click="sortColumn('id')" style="cursor: pointer;">id</th>
          <th scope="col" @click="sortColumn('username')" style="cursor: pointer;">用户名</th>
          <th scope="col" @click="sortColumn('email')" style="cursor: pointer;">邮箱</th>
          <th scope="col" @click="sortColumn('github_openid')" style="cursor: pointer;">GitHub</th>
          <th scope="col" @click="sortColumn('linuxdo_openid')" style="cursor: pointer;">LinuxDo</th>
          <th scope="col" @click="sortColumn('create_time')" style="cursor: pointer;">创建时间</th>
          <th scope="col" @click="sortColumn('app_num')" style="cursor: pointer;">APP数</th>
          <th scope="col" @click="sortColumn('category_num')" style="cursor: pointer;">分类数</th>
          <th scope="col">操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item,index) in userPageList" :key="index">
          <th scope="row">{{ index + 1 }}</th>
          <td> {{ item.id }} </td>
          <td>{{ item.username }}</td>
          <td>{{ item.email }}</td>
          <td>{{ item.githubOpenid }}</td>
          <td>{{ item.linuxdoOpenid }}</td>
          <td>{{ item.createTime }}</td>
          <td>{{ item.appNum }}</td>
          <td>{{ item.categoryNum }}</td>
          <td>
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
              禁用
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
<script>
import { ref, onMounted } from 'vue'
import userApi from '@/api/user'
export default {
  name: 'UserAdminPage',
  setup() {
    const userPageList = ref([])
    const userPageListDTO = ref({
      pageNum: 1,
      pageSize: 10,
      searchContent: '',
      orderBy: 'id desc'
    })
    const getUserPageList = async () => {
      const res = await userApi.getUserPageList(
        userPageListDTO.value.pageNum,
        userPageListDTO.value.pageSize,
        userPageListDTO.value.searchContent,
        userPageListDTO.value.orderBy
      )
      console.log(res)
      userPageList.value = res.data.items
    }
    const sortColumn = (field) => {
      // 判断当前排序字段和顺序，切换正序或倒序
      if (userPageListDTO.value.orderBy === `${field} asc`) {
        userPageListDTO.value.orderBy = `${field} desc`;
      } else {
        userPageListDTO.value.orderBy = `${field} asc`;
      }
      console.log(userPageListDTO.orderBy)
      getUserPageList(); // 重新获取数据
    }
    onMounted(() => {
      getUserPageList()
    })
    return {
      userPageList,
      userPageListDTO,
      getUserPageList,
      sortColumn
    }
  }
}
</script>