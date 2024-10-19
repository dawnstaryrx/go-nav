<template>
  <div class="container mt-4" style="margin: 0px;">
    <!-- 添加分类按钮 -->
    <button class="btn btn-primary" @click="openModal('addCategoryModal')">添加分类</button>

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
      <table class="table table-striped table-hover text-center">
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
              <button class="btn btn-primary btn-sm" @click="categoryDTO=item;openModal('updateCategoryModal')">编辑</button>
              <!-- 添加编辑分类模态框 -->
              <div class="modal fade text-start" id="updateCategoryModal" tabindex="-1" aria-labelledby="updateCategoryModalLabel" aria-hidden="true" @click="getCategoryPageList()">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <h5 class="modal-title" id="addCategoryModalLabel">编辑分类</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" @click="categoryDTO=origincategoryDTO"></button>
                      </div>
                      <div class="modal-body">
                        <form @submit.prevent="submitUpdateCategory">
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
                          <button type="submit" class="btn btn-primary">确认修改</button>
                        </form>
                      </div>
                    </div>
                  </div>
                </div>
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

    // 打开模态框
    const openModal = (name) => {
      const modalElement = document.getElementById(name);
      modal = new Modal(modalElement);
      modal.show();
    };

    // 新增分类
    const submitCategory = async () => {
      try {
        const res = await categoryApi.addCategory(categoryDTO.value);
        if (res.code === 0) {
          alertUtil.message('分类添加成功');
          resetForm();  // 清空表单
          modal.hide(); // 关闭模态框
          getCategoryPageList()
        } else {
          alertUtil.message('添加分类失败，请重试', 'danger');
        }
      } catch (error) {
        console.error(error);
      }
    };
    const getCategoryPageListData = ref({
      pageNum: 1,
      pageSize: 10
    });
    // 删除分类
    const submitDeleteCategory = async () => {
      try {
        const res = await categoryApi.deleteCategory(categoryDTO.value.id);
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
    // 修改分类
    const submitUpdateCategory = async () => {
      try {
        const res = await categoryApi.updateCategory(categoryDTO.value);
        if (res.code === 0) {
          alertUtil.message('分类修改成功');
          resetForm();  // 清空表单
          modal.hide(); // 关闭模态框
        } else {
          alertUtil.message('修改分类失败，请重试', 'danger');
        }
      } catch (error) {
        console.error(error);
      }
    }

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
      submitUpdateCategory,
      submitDeleteCategory,
    };
  }
};
</script>

<style scoped>
.container {
  /* max-width: 600px; */
}
</style>
