<template>
  <!-- 每页条数选择 -->
  <div class="mb-3 d-flex justify-content-start col">
    <label for="pageSizeSelect" class="me-2"  style="line-height: 38px;">每页显示:</label>
    <select id="pageSizeSelect" class="form-select w-auto" v-model="userPageListDTO.pageSize" @change="changePageSize">
      <option value="10">10</option>
      <option value="20">20</option>
      <option value="30">30</option>
      <option value="50">50</option>
    </select>
  </div>
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
          <th scope="col" @click="sortColumn('create_time')" style="cursor: pointer;">注册时间</th>
          <th scope="col" @click="sortColumn('app_num')" style="cursor: pointer;">APP数</th>
          <th scope="col" @click="sortColumn('category_num')" style="cursor: pointer;">分类数</th>
          <th scope="col">操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item,index) in userPageList" :key="index">
          <th scope="row">{{ index + 1 }}</th>
          <td> {{ item.id }} </td>
          <td :style="item.role == 2 ? 'background-color: antiquewhite;' : ''">{{ item.username }}</td>
          <td>{{ item.email }}</td>
          <td>{{ item.githubOpenid }}</td>
          <td>{{ item.linuxdoOpenid }}</td>
          <td>{{ item.createTime }}</td>
          <td>{{ item.appNum }}</td>
          <td>{{ item.categoryNum }}</td>
          <td>
            <button type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#exampleModal">
              修改
            </button>
          </td>
        </tr>
      </tbody>
    </table>
    <!-- 分页条 -->
    <nav aria-label="Page navigation">
      <ul class="pagination justify-content-center">
        <li class="page-item" :class="{ disabled: userPageListDTO.pageNum === 1 }">
          <a class="page-link" href="#" @click.prevent="changePage(userPageListDTO.pageNum - 1)">上一页</a>
        </li>
        <li class="page-item" v-for="page in totalPages" :key="page" :class="{ active: page === userPageListDTO.pageNum }">
          <a class="page-link" href="#" @click.prevent="changePage(page)">{{ page }}</a>
        </li>
        <li class="page-item" :class="{ disabled: userPageListDTO.pageNum === totalPages }">
          <a class="page-link" href="#" @click.prevent="changePage(userPageListDTO.pageNum + 1)">下一页</a>
        </li>
      </ul>
    </nav>
  </div>
</template>
<script>
import { ref, onMounted } from 'vue'
import userApi from '@/api/user'
export default {
  name: 'UserAdminPage',
  setup() {
    const totalPages = ref(1); // 总页数
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
      totalPages.value = parseInt(res.data.total / userPageListDTO.value.pageSize);
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
    const changePage = (page) => {
      if (page < 1 || page > totalPages.value) return; // 检查页数有效性
      userPageListDTO.value.pageNum = page;
      getUserPageList(); // 重新获取列表
    };
    // 修改每页条数
    const changePageSize = () => {
      userPageListDTO.value.pageNum = 1; // 修改每页条数时，重置为第一页
      getUserPageList(); // 重新获取数据
    };
    onMounted(() => {
      getUserPageList()
    })
    return {
      userPageList,
      userPageListDTO,
      getUserPageList,
      sortColumn,
      changePage,
      changePageSize,
      totalPages,
    }
  }
}
</script>