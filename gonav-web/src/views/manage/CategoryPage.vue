<template>
  <div class="container mt-4" style="margin: 0px;">
    <!-- 添加分类按钮 -->
    <button class="btn btn-primary" @click="openModal">添加分类</button>

    <!-- 添加分类模态框 -->
    <div class="modal fade" id="addCategoryModal" tabindex="-1" aria-labelledby="addCategoryModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="addCategoryModalLabel">添加分类</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form @submit.prevent="submitCategory">
              <div class="mb-3">
                <label for="parentId" class="form-label">父分类ID</label>
                <input v-model="categoryDTO.parentId" type="number" class="form-control" id="parentId" placeholder="输入父分类ID">
              </div>
              <div class="mb-3">
                <label for="name" class="form-label">分类名称</label>
                <input v-model="categoryDTO.name" type="text" class="form-control" id="name" placeholder="输入分类名称">
              </div>
              <div class="mb-3">
                <label for="description" class="form-label">描述</label>
                <input v-model="categoryDTO.description" type="text" class="form-control" id="description" placeholder="输入分类描述">
              </div>
              <div class="mb-3">
                <label for="weight" class="form-label">权重</label>
                <input v-model="categoryDTO.weight" type="number" class="form-control" id="weight" placeholder="输入权重">
              </div>
              <div class="mb-3">
                <label for="status" class="form-label">状态</label>
                <select v-model="categoryDTO.status" class="form-select" id="status">
                  <option value="1">启用</option>
                  <option value="0">禁用</option>
                </select>
              </div>
              <button type="submit" class="btn btn-primary">确认添加</button>
            </form>
          </div>
        </div>
      </div>
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
            <td>{{ item.status }}</td>
            <td>@mdo</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';
import categoryApi from '@/api/category.js';
import { Modal } from 'bootstrap';

export default {
  setup() {
    // 定义分类数据
    const categoryDTO = ref({
      parentId: null,
      name: '',
      description: '',
      weight: null,
      status: 1,
    });
    const categoryList = ref([]);

    // 保存模态框实例
    let modal = null;

    // 打开模态框
    const openModal = () => {
      const modalElement = document.getElementById('addCategoryModal');
      modal = new Modal(modalElement);
      modal.show();
    };

    // 提交分类信息
    const submitCategory = async () => {
      try {
        const res = await categoryApi.addCategory(categoryDTO.value);
        if (res.code === 0) {
          alert('分类添加成功');
          resetForm();  // 清空表单
          modal.hide();  // 关闭模态框
        } else {
          alert('添加分类失败，请重试');
        }
      } catch (error) {
        console.error(error);
      }
    };
    const getCategoryPageListData = ref({
      pageNum: 1,
      pageSize: 10
    });

    // 获取分类分页列表
    const getCategoryPageList = async () => {
      try {
        const res = await categoryApi.getCategoryPageList(getCategoryPageListData.value.pageNum, getCategoryPageListData.value.pageSize);
        console.log(res);
        categoryList.value = res.data.items;
        console.log(categoryList.value);
      } catch (error) {
        console.error(error);
      }
    };
    getCategoryPageList()
    // 重置表单
    const resetForm = () => {
      categoryDTO.value = {
        parentId: null,
        name: '',
        description: '',
        weight: null,
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
      categoryList
    };
  }
};
</script>

<style scoped>
.container {
  /* max-width: 600px; */
}
</style>
