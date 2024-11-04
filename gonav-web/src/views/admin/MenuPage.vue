<template>
  <div class="container mt-4" style="margin: 0px;">
    <div class="row">
      <!-- 添加menu按钮 -->
      <div class="col">
        <button class="btn btn-primary" @click="openModal('addMenuModal')">添加菜单</button>
      </div>
    </div>
    <!-- 添加menu模态框 -->
    <div class="modal fade" id="addMenuModal" tabindex="-1" aria-labelledby="addMenuModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="addMenuModalLabel">添加菜单</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form @submit.prevent="submitMenu">
              <div class="mb-3">
                <label for="add-name" class="form-label">名称</label>
                <input v-model="menuDTO.title" type="text" class="form-control" id="add-name" placeholder="输入menu名称">
              </div>
              <div class="mb-3">
                <label for="add-url" class="form-label">URL</label>
                <input v-model="menuDTO.url" type="text" class="form-control" id="add-url" placeholder="输入menu URL">
              </div>
              <div class="mb-3">
                <label for="add-weight" class="form-label">权重</label>
                <input v-model="menuDTO.weight" type="number" class="form-control" id="add-weight" placeholder="输入权重">
              </div>
              <div class="mb-3">
                <label for="add-status" class="form-label">类型</label>
                <select v-model="menuDTO.type" class="form-select" id="add-status">
                  <option value="0">顶部</option>
                  <option value="1">底部</option>
                </select>
              </div>
              <button type="submit" class="btn btn-primary" style="float: inline-end;">确认添加</button>
            </form>
          </div>
        </div>
      </div>
    </div>
    <div class="accordion accordion-flush" id="accordionFlushExample" style="margin-top: 30px;">
      <div class="accordion-item">
        <h2 class="accordion-header">
          <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseOne" aria-expanded="false" aria-controls="flush-collapseOne">
            顶部导航菜单
          </button>
        </h2>
        <div id="flush-collapseOne" class="accordion-collapse collapse" data-bs-parent="#accordionFlushExample">
          <div class="accordion-body">
            <table class="table text-center">
              <thead>
                <tr>
                  <th scope="col">#</th>
                  <th scope="col">名称</th>
                  <th scope="col">网址</th>
                  <th scope="col">权重</th>
                  <th scope="col">创建时间</th>
                  <th scope="col">操作</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(item, index) in menuTopList" :key="index">
                  <th scope="row">{{ index + 1 }}</th>
                  <td>{{ item.title }}</td>
                  <td>{{ item.url }}</td>
                  <td>{{ item.weight }}</td>
                  <td>{{ item.createTime }}</td>
                  <td>
                    <!-- <button class="btn" @click="editMenu(item)">编辑</button> -->
                    <button class="btn btn-danger" @click="deleteMenu(item.id)">删除</button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <div class="accordion-item">
        <h2 class="accordion-header">
          <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseTwo" aria-expanded="false" aria-controls="flush-collapseTwo">
            友情链接
          </button>
        </h2>
        <div id="flush-collapseTwo" class="accordion-collapse collapse" data-bs-parent="#accordionFlushExample">
          <div class="accordion-body">
            <table class="table text-center">
              <thead>
                <tr>
                  <th scope="col">#</th>
                  <th scope="col">名称</th>
                  <th scope="col">网址</th>
                  <th scope="col">权重</th>
                  <th scope="col">创建时间</th>
                  <th scope="col">操作</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(item, index) in menuBottomList" :key="index">
                  <th scope="row">{{ index + 1 }}</th>
                  <td>{{ item.title }}</td>
                  <td>{{ item.url }}</td>
                  <td>{{ item.weight }}</td>
                  <td>{{ item.createTime }}</td>
                  <td>
                    <!-- <button class="btn" @click="editMenu(item)">编辑</button> -->
                    <button class="btn btn-danger" @click="deleteMenu(item.id)">删除</button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import menuApi from '../../api/menu';
import { onMounted, ref } from 'vue';
import { Modal } from 'bootstrap';
import alertUtil from "@/utils/alert";

export default {
  setup(){
    // 定义数据
    const menuDTO = ref({
      id: null,
      title: '',
      url: '',
      type: 0,
      weight: 0,
    })
    const menuTopList = ref([]);
    const menuBottomList = ref([]);
    // 打开模态框
    let modal = null;
    const openModal = (name) => {
      const modalElement = document.getElementById(name);
      modal = new Modal(modalElement);
      modal.show();
    };
    // 新增Menu
    const submitMenu = async () => {
      try {
        const res = await menuApi.addMenu(menuDTO.value);
        if (res.code === 0) {
          alertUtil.message('菜单添加成功');
          resetForm();  // 清空表单
          modal.hide(); // 关闭模态框
          getTopMenu()
          getBottomMenu()
          // getAppPageList()
        } else {
          alertUtil.message('添加菜单失败，请重试', 'danger');
        }
      } catch (error) {
        console.error(error);
      }
    };
    // 删除菜单
    const deleteMenu = async (id) => {
      try {
        const res = await menuApi.deleteMenu(id);
        if (res.code === 0) {
          alertUtil.message('菜单删除成功');
          getTopMenu()
          getBottomMenu()
        } else {
          alertUtil.message('删除菜单失败，请重试', 'danger');
        }
      } catch (error) {
        console.error(error);
      }
    }
    // 重置表单
    const resetForm = () => {
      menuDTO.value = {
        id: null,
        title: '',
        url: '',
        weight: 0,
        type: 0
      };
    };
    // 获取TopMenu
    const getTopMenu = async() => {
      try {
        const res = await menuApi.getMenuList(0);
        menuTopList.value = res.data;
      } catch (error) {
        console.error(error);
      }
    }
    // 获取BottomMenu
    const getBottomMenu = async() => {
      try {
        const res = await menuApi.getMenuList(1);
        menuBottomList.value = res.data;
      } catch (error) {
        console.error(error);
      }
    }
    onMounted(() => {
      getTopMenu();
      getBottomMenu();
    });
    return{
      menuDTO,
      submitMenu,
      openModal,
      menuTopList,
      menuBottomList,
      deleteMenu
    }
  }
}
</script>