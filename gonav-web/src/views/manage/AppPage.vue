<template>
  <div class="container mt-4" style="margin: 0px;">
    <div class="row">
      <!-- 添加分类按钮 -->
      <div class="col">
        <button class="btn btn-primary" @click="openModal('addAppModal')">添加应用</button>
      </div>
      
      <!-- 每页条数选择 -->
      <div class="mb-3 d-flex justify-content-end col">
        <label for="pageSizeSelect" class="me-2"  style="line-height: 38px;">每页显示:</label>
        <select id="pageSizeSelect" class="form-select w-auto" v-model="getAppPageListData.pageSize" @change="changePageSize">
          <option value="10">10</option>
          <option value="20">20</option>
          <option value="30">30</option>
          <option value="50">50</option>
        </select>
      </div>
    </div>

    <!-- 添加APP模态框 -->
    <div class="modal fade" id="addAppModal" tabindex="-1" aria-labelledby="addAppModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="addAppModalLabel">添加应用</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form @submit.prevent="submitApp">
              <div class="mb-3">
                <label for="add-name" class="form-label">名称</label>
                <input v-model="appDTO.name" type="text" class="form-control" id="add-name" placeholder="输入APP名称（非必填）">
              </div>
              <!-- <div class="mb-3">
                <label for="add-categoryId" class="form-label">所属分类</label>
                <input v-model="appDTO.categoryId" type="number" class="form-control" id="add-categoryId" placeholder="输入分类ID">
              </div> -->
              <div class="mb-3">
                <label for="add-categoryId" class="form-label">所属分类</label>
                <select v-model="appDTO.categoryId" class="form-select" id="add-categoryId">
                    <option value="" disabled>请选择分类</option>
                    <option v-for="category in categoryNowList" :key="category.id" :value="category.id">
                        {{ category.name }} <!-- 假设每个分类对象都有 id 和 name 属性 -->
                    </option>
                </select>
              </div>
              <div class="mb-3">
                <label for="add-url" class="form-label">网址</label>
                <input v-model="appDTO.url" type="text" class="form-control" id="add-url" placeholder="输入APP 网址">
              </div>
              <!-- <div class="mb-3">
                <label for="add-icon-url" class="form-label">图标</label>
                <input v-model="appDTO.iconUrl" type="text" class="form-control" id="add-icon-url" placeholder="输入APP 图标 URL">
              </div> -->
              <div class="mb-3">
                <label for="add-description" class="form-label">描述</label>
                <input v-model="appDTO.description" type="text" class="form-control" id="add-description" placeholder="输入APP描述（非必填）"></input>
              </div>
              <div class="mb-3">
                <label for="add-weight" class="form-label">权重</label>
                <input v-model="appDTO.weight" type="number" class="form-control" id="add-weight" placeholder="输入权重">
              </div>
              <div class="mb-3">
                <label for="add-status" class="form-label">状态</label>
                <select v-model="appDTO.status" class="form-select" id="add-status">
                  <option value="1">全部可见</option>
                  <option value="2">登录后可见</option>
                  <option value="3">本人可见</option>
                  <option value="4">隐藏</option>
                </select>
              </div>
              <button type="submit" class="btn btn-primary" style="float: inline-end;">确认添加</button>
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
            <th scope="col">名称</th>
            <th scope="col">所属分类</th>
            <th scope="col">图标</th>
            <th scope="col">描述</th>
            <th scope="col">URL</th>
            <th scope="col">权重</th>
            <th scope="col">状态</th>
            <th scope="col">点击数</th>
            <th scope="col">操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in appList">
            <th scope="row">{{index + 1}}</th>
            <td>{{ item.name }}</td>
            <td>{{ item.categoryName }}</td>
            <td>
              <img v-if="item.iconUrl !== ''" :src=item.iconUrl alt="" class="img-thumbnail" width="40" height="40" />
              <!-- {{ item.iconUrl }} -->
            </td>
            <td>{{ item.description.slice(0, 10) }}{{ item.description.length > 10 ? '...' : '' }}</td>
            <td>{{ item.url }}</td>
            <td>{{ item.weight }}</td>
            <td>
              <span v-if="item.status === 1" class="badge bg-success">公开</span>
              <span v-else-if="item.status === 2" class="badge bg-success">登录</span>
              <span v-else-if="item.status === 3" class="badge bg-success">私有</span>
              <span v-else class="badge bg-danger">隐藏</span>
            </td>
            <td>{{ item.clickCount }}</td>
            <td>
              <button class="btn btn-primary btn-sm" @click="appDTO=item;openModal('updateAppModal')">编辑</button>
              <!-- 添加编辑应用模态框 -->
              <div class="modal fade text-start" id="updateAppModal" tabindex="-1" aria-labelledby="updateAppModalLabel" aria-hidden="true" @click="getAppPageList()">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <h5 class="modal-title" id="addCategoryModalLabel">编辑应用</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" @click="categoryDTO=origincategoryDTO"></button>
                      </div>
                      <div class="modal-body">
                        <form @submit.prevent="submitUpdateApp">
                          <div class="mb-3">
                            <label for="name" class="form-label">应用名称</label>
                            <input v-model="appDTO.name" type="text" class="form-control" id="name" placeholder="输入应用名称">
                          </div>
                          <div class="mb-3">
                            <label for="url" class="form-label">应用地址</label>
                            <input v-model="appDTO.url" type="text" class="form-control" id="url" placeholder="输入应用地址">
                          </div>
                          <div class="mb-3">
                            <label for="iconUrl" class="form-label">图标</label>
                            <input v-model="appDTO.iconUrl" type="text" class="form-control" id="iconUrl" placeholder="输入图标地址">
                          </div>
                          <!-- <div class="mb-3">
                            <label for="categoryName" class="form-label">分类</label>
                            <input v-model="appDTO.categoryName" type="text" class="form-control" id="categoryName" disabled>
                          </div> -->
                          <!-- <div class="mb-3">
                            <label for="categoryId" class="form-label">分类</label>
                            <input v-model="appDTO.categoryId" type="number" class="form-control" id="categoryId">
                          </div> -->
                          <div class="mb-3">
                            <label for="categoryId" class="form-label">分类</label>
                            <select v-model="appDTO.categoryId" class="form-select" id="categoryId">
                                <option value="" disabled>请选择分类</option>
                                <option v-for="category in categoryNowList" :key="category.id" :value="category.id">
                                    {{ category.name }} <!-- 假设每个分类对象都有 id 和 name 属性 -->
                                </option>
                            </select>
                          </div>
                          <div class="mb-3">
                            <label for="description" class="form-label">描述</label>
                            <input v-model="appDTO.description" type="text" class="form-control" id="description" placeholder="输入应用描述信息">
                          </div>
                          <div class="mb-3">
                            <label for="weight" class="form-label">权重</label>
                            <input v-model="appDTO.weight" type="number" class="form-control" id="weight" placeholder="输入权重">
                          </div>
                          <div class="mb-3">
                            <label for="weight" class="form-label">点击数</label>
                            <input v-model="appDTO.clickCount" type="number" class="form-control" id="clickCount" placeholder="输入点击数">
                          </div>
                          <div class="mb-3">
                            <label for="status" class="form-label">状态</label>
                            <select v-model="appDTO.status" class="form-select" id="status">
                              <option value="1">全部可见</option>
                              <option value="2">登陆后可见</option>
                              <option value="3">本人可见</option>
                              <option value="4">隐藏</option>
                            </select>
                          </div>
                          <button type="submit" class="btn btn-primary">确认修改</button>
                        </form>
                      </div>
                    </div>
                  </div>
                </div>
              <button class="btn btn-danger btn-sm" @click="appDTO=item;openModal('deleteAppModal')" style="margin-left: 5px;">删除</button>
              <!-- 添加编辑分类模态框 -->
              <div class="modal fade text-start" id="deleteAppModal" tabindex="-1" aria-labelledby="deleteAppModalLabel" aria-hidden="true" @click="getAppPageList()">
                <div class="modal-dialog">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h5 class="modal-title" id="deleteAppModalLabel">删除分类</h5>
                      <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                      确认删除应用{{ appDTO.name }}？
                      <form @submit.prevent="submitDeleteApp">
                        <button type="submit" class="btn btn-primary" style="float: inline-end;">确认删除</button>
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
          <li class="page-item" :class="{ disabled: getAppPageListData.pageNum === 1 }">
            <a class="page-link" href="#" @click.prevent="changePage(getAppPageListData.pageNum - 1)">上一页</a>
          </li>
          <li class="page-item" v-for="page in totalPages" :key="page" :class="{ active: page === getAppPageListData.pageNum }">
            <a class="page-link" href="#" @click.prevent="changePage(page)">{{ page }}</a>
          </li>
          <li class="page-item" :class="{ disabled: getAppPageListData.pageNum === totalPages }">
            <a class="page-link" href="#" @click.prevent="changePage(getAppPageListData.pageNum + 1)">下一页</a>
          </li>
        </ul>
      </nav>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import appApi from '@/api/app.js';
import { Modal } from 'bootstrap';
import alertUtil from "@/utils/alert";
import categoryApi from '@/api/category.js';

export default {
  setup() {
    // 定义APP数据
    const appDTO = ref({
      id: null,
      name: '',
      description: '',
      url: '',
      iconUrl: '',
      weight: 1,
      status: 1,
      clickCount: 0,
      categoryId: null,
    });
    const appList = ref([]);
    const categoryNowList = ref([]);
    // 获取分类不分页列表
    const getCategoryList = async () => {
      try {
        const res = await categoryApi.getCategoryList();
        console.log(res);
        categoryNowList.value = res.data;
      } catch (error) {
        console.error(error);
      }
    };
    onMounted(() => {
      getCategoryList();
    });
    // 保存模态框实例
    let modal = null;
    const totalPages = ref(1); // 总页数
    const getAppPageListData = ref({
      pageNum: 1,
      pageSize: 10,
      categoryId: null,
      status: null,
      searchContent: null
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
      getAppPageListData.value.pageNum = page;
      getAppPageList(); // 重新获取列表
    };
    // 修改每页条数
    const changePageSize = () => {
      getAppPageListData.value.pageNum = 1; // 修改每页条数时，重置为第一页
      getAppPageList(); // 重新获取数据
    };
    // 新增分类
    const submitApp = async () => {
      try {
        const res = await appApi.addApp(appDTO.value);
        if (res.code === 0) {
          alertUtil.message('分类添加成功');
          resetForm();  // 清空表单
          modal.hide(); // 关闭模态框
          getAppPageList()
        } else {
          alertUtil.message('添加分类失败，请重试', 'danger');
        }
      } catch (error) {
        console.error(error);
      }
    };
    // 删除分类
    const submitDeleteApp = async () => {
      try {
        const res = await appApi.deleteApp(appDTO.value.id);
        if (res.code === 0) {
          alertUtil.message('分类删除成功');
          resetForm();  // 清空表单
          modal.hide(); // 关闭模态框
          getAppPageList()
        } else {
          alertUtil.message('删除分类失败，请重试', 'danger');
        }
      } catch (error) {
        console.error(error);
      }
    }
    // 修改分类
    const submitUpdateApp = async () => {
      try {
        const res = await appApi.updateApp(appDTO.value);
        if (res.code === 0) {
          alertUtil.message('分类修改成功');
          resetForm();  // 清空表单
          modal.hide(); // 关闭模态框
          getAppPageList()
        } else {
          alertUtil.message('修改分类失败，请重试', 'danger');
        }
      } catch (error) {
        console.error(error);
      }
    }

    // 获取分类分页列表
    const getAppPageList = async () => {
      try {
        const res = await appApi.getAppPageList(
          getAppPageListData.value.pageNum, 
          getAppPageListData.value.pageSize,
          getAppPageListData.value.categoryId,
          getAppPageListData.value.status,
          getAppPageListData.value.searchContent
        );
        console.log(res);
        appList.value = res.data.items;
        console.log(appList.value);
        totalPages.value = parseInt(res.data.total / getAppPageListData.value.pageSize) + 1;
      } catch (error) {
        console.error(error);
      }
    };
    getAppPageList()
    // 重置表单
    const resetForm = () => {
      appDTO.value = {
        id: null,
        name: '',
        description: '',
        url: '',
        iconUrl: '',
        weight: 1,
        status: 1,
        clickCount: 0,
        categoryId: null,
      };
    };

    // 返回模板使用的变量和方法
    return {
      appDTO,
      openModal,
      submitApp,
      resetForm,
      getAppPageList,
      getAppPageListData,
      submitDeleteApp,
      submitUpdateApp,
      changePage,
      totalPages,
      changePageSize,
      appList,
      categoryNowList
    };
  }
};
</script>

<style scoped>
.container {
  /* max-width: 600px; */
}
</style>
