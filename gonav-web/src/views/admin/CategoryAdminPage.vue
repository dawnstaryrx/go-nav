<template>
  <div class="container mt-4" style="margin: 0px;">
    <!-- 每页条数选择 -->
    <div class="mb-3 d-flex justify-content-start col">
      <label for="pageSizeSelect" class="me-2"  style="line-height: 38px;">每页显示:</label>
      <select id="pageSizeSelect" class="form-select w-auto" v-model="getCategoryPageListData.pageSize" @change="changePageSize">
        <option value="10">10</option>
        <option value="20">20</option>
        <option value="30">30</option>
        <option value="50">50</option>
      </select>
    </div>
    <!-- 列表 -->
    <div>
      <table class="table">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">id</th>
            <th scope="col">名称</th>
            <th scope="col">父分类</th>
            <th scope="col">权重</th>
            <th scope="col">状态</th>
            <th scope="col">操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in categoryList">
            <th scope="row">{{index + 1}}</th>
            <td>{{ item.id }}</td>
            <td>{{ item.name }}</td>
            <td>{{ item.parentName }}</td>
            <td>{{ item.weight }}</td>
            <td>
              <span v-if="item.status === 1" class="badge bg-success">启用</span>
              <span v-else class="badge bg-danger">禁用</span>
            </td>
            <td>
              <button class="btn btn-danger btn-sm" @click="categoryDTO=item;openModal('deleteCategoryModal')">删除</button>
              <!-- 添加编辑分类模态框 -->
              <div class="modal fade text-start" id="deleteCategoryModal" tabindex="-1" aria-labelledby="deleteCategoryModalLabel" aria-hidden="true" @click="getCategoryPageList()">
                <div class="modal-dialog">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h5 class="modal-title" id="addCategoryModalLabel">删除分类</h5>
                      <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                      <form @submit.prevent="submitDeleteCategory">
                        <button type="submit" class="btn btn-primary">确认删除</button>
                      </form>
                    </div>
                  </div>
                </div>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
      <!-- 分页条 -->
      <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center">
          <li class="page-item" :class="{ disabled: getCategoryPageListData.pageNum === 1 }">
            <a class="page-link" href="#" @click.prevent="changePage(getCategoryPageListData.pageNum - 1)">上一页</a>
          </li>
          <li class="page-item" v-for="page in totalPages" :key="page" :class="{ active: page === getCategoryPageListData.pageNum }">
            <a class="page-link" href="#" @click.prevent="changePage(page)">{{ page }}</a>
          </li>
          <li class="page-item" :class="{ disabled: getCategoryPageListData.pageNum === totalPages }">
            <a class="page-link" href="#" @click.prevent="changePage(getCategoryPageListData.pageNum + 1)">下一页</a>
          </li>
        </ul>
      </nav>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';
import categoryApi from '@/api/category.js';
import { Modal } from 'bootstrap';
import alertUtil from "@/utils/alert";

export default {
  setup() {
    // 定义分类数据
    const categoryDTO = ref({
      id: null,
      parentId: null,
      name: '',
      description: '',
      weight: 1,
      status: 1,
    });
    const categoryList = ref([]);

    // 保存模态框实例
    let modal = null;
    const totalPages = ref(1); // 总页数
    const getCategoryPageListData = ref({
      pageNum: 1,
      pageSize: 10
    });
    // 打开模态框
    const openModal = (name) => {
      const modalElement = document.getElementById(name);
      modal = new Modal(modalElement);
      modal.show();
    };
    // 切换分页
    const changePage = (page) => {
      if (page < 1 || page > totalPages.value) return; // 检查页数有效性
      getCategoryPageListData.value.pageNum = page;
      getCategoryPageList(); // 重新获取列表
    };
    // 修改每页条数
    const changePageSize = () => {
      getCategoryPageListData.value.pageNum = 1; // 修改每页条数时，重置为第一页
      getCategoryPageList(); // 重新获取数据
    };
    // 提交分类信息
    const submitCategory = async () => {
      try {
        const res = await categoryApi.addCategory(categoryDTO.value);
        if (res.code === 0) {
          alertUtil.message('分类添加成功');
          resetForm();  // 清空表单
          modal.hide(); // 关闭模态框
        } else {
          alertUtil.message('添加分类失败，请重试', 'danger');
        }
      } catch (error) {
        console.error(error);
      }
    };
    // 删除分类
    const submitDeleteCategory = async () => {
      try {
        const res = await categoryApi.deleteCategoryAdmin(categoryDTO.value.id);
        if (res.code === 0) {
          alertUtil.message('分类删除成功');
          resetForm();  // 清空表单
          modal.hide(); // 关闭模态框
          getCategoryPageList()
        } else {
          alertUtil.message('删除分类失败，请重试', 'danger');
        }
      } catch (error) {
        console.error(error);
      }
    }
    // 获取分类分页列表
    const getCategoryPageList = async () => {
      try {
        const res = await categoryApi.getAllCategory(getCategoryPageListData.value.pageNum, getCategoryPageListData.value.pageSize);
        console.log(res);
        categoryList.value = res.data.items;
        console.log(categoryList.value);
        totalPages.value = parseInt(res.data.total / getCategoryPageListData.value.pageSize) + 1;
      } catch (error) {
        console.error(error);
      }
    };
    getCategoryPageList()
    // 重置表单
    const resetForm = () => {
      categoryDTO.value = {
        id: null,
        parentId: null,
        name: '',
        description: '',
        weight: 1,
        status: 1,
      };
    };

    // 返回模板使用的变量和方法
    return {
      categoryDTO,
      openModal,
      submitCategory,
      resetForm,
      getCategoryPageList,
      categoryList,
      submitDeleteCategory,
      getCategoryPageListData,
      changePage,
      totalPages,
      changePageSize,
    };
  }
};
</script>

<style scoped>
.container {
  /* max-width: 600px; */
}
</style>
