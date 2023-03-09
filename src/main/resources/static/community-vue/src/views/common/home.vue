<template>
  <div class="mod-home">
    <h2>数据概览</h2>

    <!-- <h4>
      美食先知是基于Springboot MybatisPlus Shiro Jwt Vue Uniapp Redis
      MySQL构建的美食先知app平台
    </h4> -->
    <!--头部-->
    <base-info ref="baseInfo"/>

  </div>
</template>

<script>
import baseInfo from "./components/baseInfo";

export default {
  components: { baseInfo },
  data() {
    return {

    };
  },

};
</script>

<style>
.mod-home {
  line-height: 1.5;
}
</style>

