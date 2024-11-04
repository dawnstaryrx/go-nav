<template>
  <h3 style="margin-top: 10px;">网站设置</h3>
  <div class="mb-3">
    <label for="siteName" class="form-label">网站标题</label>
    <input v-model="siteName.valueVarchar" type="text" class="form-control" id="siteName" placeholder="请输入网站标题...">
  </div>
  <div class="mb-3">
    <label for="siteLogo" class="form-label">网站Logo</label>
    <input v-model="siteLogo.valueVarchar" type="text" class="form-control" id="siteLogo" placeholder="请输入网站Logo URL地址...">
  </div>
  <button class="btn btn-primary"> 修改 </button>
  <h3 style="margin-top: 50px;">页脚设置</h3>
  <div class="mb-3">
    <label for="companyName" class="form-label">公司名称</label>
    <input v-model="companyName.valueVarchar" type="text" class="form-control" id="companyName" placeholder="请输入公司名称...">
  </div>
  <div class="mb-3">
    <label for="icp" class="form-label">ICP备案号</label>
    <input v-model="icp.valueVarchar" type="text" class="form-control" id="icp" placeholder="请输入ICP备案号...">
  </div>
  <div class="mb-3">
    <label for="police" class="form-label">公安备案号</label>
    <input v-model="police.valueVarchar" type="text" class="form-control" id="police" placeholder="请输入公安备案号...">
  </div>
  <button class="btn btn-primary" @click="updateBottomSetting()"> 修改 </button>
  <h3 style="margin-top: 50px;">登录设置</h3>
  <div class="form-check form-switch">
    <input v-model="loginGithub.valueVarchar" class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault">
    <label class="form-check-label" for="flexSwitchCheckDefault">GitHub登录</label>
  </div>
  <div class="form-check form-switch">
    <input v-model="loginLinuxdo.valueVarchar" class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckChecked" checked>
    <label class="form-check-label" for="flexSwitchCheckChecked">LinuxDo登录</label>
  </div>
  <button class="btn btn-primary" @click="updateLoginSetting()">保存</button>
  <h3 style="margin-top: 50px;">展示设置</h3>
  <div class="form-check form-switch">
    <input v-model="githubLogo.valueVarchar" class="form-check-input" type="checkbox" role="switch" id="githubLogoCheck">
    <label class="form-check-label" for="githubLogoCheck">GitHub Logo</label>
  </div>
  <button class="btn btn-primary" @click="updateGithubLogoSetting(githubLogo)">保存</button>
</template>
<script>
import { onMounted, ref } from 'vue'
import settingApi from '@/api/setting'
export default {
  name: 'SettingPage',
  setup() {
    const companyName = ref({
      key: 'bottom_company_name',
      valueVarchar: '',
      valueText: ''
    })
    const icp = ref({
      key: 'bottom_icp',
      valueVarchar: '',
      valueText: ''
    })
    const police = ref({
      key: 'bottom_police',
      valueVarchar: '',
      valueText: ''
    })
    const siteName = ref({
      key: 'site_name',
      valueVarchar: '',
      valueText: ''
    })
    const siteLogo = ref({
      key: 'site_logo',
      valueVarchar: '',
      valueText: ''
    })
    const loginGithub = ref({
      key: 'login_github',
      valueVarchar: '',
      valueText: ''
    })
    const loginLinuxdo = ref({
      key: 'login_linuxdo',
      valueVarchar: '',
      valueText: ''
    })
    const githubLogo = ref({
      key: 'github_logo',
      valueVarchar: '',
      valueText: ''
    })
    const getBottomSetting = async () => {
      const res = await settingApi.getBottomSetting()
      companyName.value.valueVarchar = res.data.companyName
      icp.value.valueVarchar = res.data.icp
      police.value.valueVarchar = res.data.police
    }
    const getSiteSetting = async () => {
      const res = await settingApi.getSiteSetting()
      siteName.value.valueVarchar = res.data.name
      siteLogo.value.valueVarchar = res.data.logo
    }
    const getLoginSetting = async () => {
      const res1 = await settingApi.getSetting('login_github')
      loginGithub.value.valueVarchar = res1.data.valueVarchar
      const res2 = await settingApi.getSetting('login_linuxdo')
      loginLinuxdo.value.valueVarchar = res2.data.valueVarchar
    }
    const getGitHubLogoSetting = async () => {
      const res = await settingApi.getSetting('github_logo')
      githubLogo.value.valueVarchar = res.data.valueVarchar
    }
    const updateSetting = async (data) => {
      await settingApi.updateSetting(data)
    }
    const updateBottomSetting = () => {
      updateSetting(companyName.value)
      updateSetting(icp.value)
      updateSetting(police.value)
    }
    const updateLoginSetting = () => {
      updateSetting(loginGithub.value)
      updateSetting(loginLinuxdo.value)
    }
    const updateGithubLogoSetting = () => {
      updateSetting(githubLogo.value)
    }
    onMounted(() => {
      getBottomSetting(),
      getSiteSetting(),
      getLoginSetting(),
      getGitHubLogoSetting()
    })
    return {
      companyName,
      icp,
      police,
      updateBottomSetting,
      siteName,
      siteLogo,
      loginGithub,
      loginLinuxdo,
      updateLoginSetting,
      githubLogo,
      updateGithubLogoSetting
    }
  }
}
</script>