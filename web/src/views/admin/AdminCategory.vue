<template>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
<!--      <a-input-search-->
<!--          v-model:value="search_value"-->
<!--          placeholder="input name"-->
<!--          enter-button="Search"-->
<!--          style="width:200px"-->
<!--          @search="onSearch"-->
<!--      />-->
      <p>
        <a-form layout="inline" :model="param">
          <a-form-item>
            <a-button type="primary" @click="handleQuery()">
              Search
            </a-button>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="add" size="medium">
              New
            </a-button>
          </a-form-item>
        </a-form>
      </p>

      <a-table
          :columns="columns"
          :row-key="record => record.id"
          :data-source="level1"
          :loading="loading"
          :pagination="false"
      >
        <template #cover="{ text: cover}">
          <img v-if="cover" :src="cover"  :alt = "avatar"/>
        </template>
        <template v-slot:action="{ text, record }">
          <a-space size="small">
            <a-button type="primary" @click="edit(record)">
              Edit
            </a-button>
            <a-popconfirm
                title="Are you sure delete this Category?"
                ok-text="Yes"
                cancel-text="No"
                @confirm="confirm(record.id)"
                @cancel="cancel"
            >
            <a-button type="danger">
              Remove
            </a-button>
            </a-popconfirm>
          </a-space>
        </template>
      </a-table>
    </a-layout-content>
  </a-layout>

  <!-- 编辑框或新加内容框 -->
  <a-modal
    title="分类表单"
    v-model:visible="modalVisible"
    :confirm-loading="modalLoading"
    @ok="handleModalOk"
  >
    <a-form :model="category" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
      <a-form-item label="Name">
        <a-input v-model:value="category.name" />
      </a-form-item>
      <a-form-item label="Parent">
        <a-input v-model:value="category.parent" type="textarea" />
      </a-form-item>
      <a-form-item label="Sort">
        <a-input v-model:value="category.sort" type="textarea" />
      </a-form-item>
    </a-form>
  </a-modal>

</template>
<script lang = "ts">
  import {defineComponent, onMounted,ref} from 'vue';
  import axios from "axios";
  import {message} from "ant-design-vue";
  import {Tool} from "@/util/tool";

  export default defineComponent({
    name: 'AdminCategory',
    setup: function () {
      const param = ref();
      param.value = {}
      const categorys = ref();
      const level1 = ref();
      const search_value = ref();
      // search_value.value = {}; // 需要加Value才能赋值
      //响应式变量，不用使用.value调用？还是不太懂响应式变量
      // const pagination = ref({
      //   current: 1,
      //   pageSize: 10,
      //   total: 0
      // });

      const loading = ref(false);
      const columns = [
        {
          title: 'Name',
          dataIndex: 'name',  // 数据源吧，把查出结果的列名匹配上
          slots: {customRender: 'name'}
        },
        {
          title: 'Parent',
          dataIndex: 'parent',
        },
        {
          title: 'Sort',
          dataIndex: 'sort',
        },
        {
          title: 'Action',
          dataIndex: 'action',
          slots: {customRender: 'action'}
        }
      ];

      /**
       *  数据查询
       */
      const handleQuery = () => {
        loading.value = true;
        axios.get("/category/all").then((response) => {
          loading.value = false;
          const data = response.data;
          if (data.success) {
            categorys.value = data.content;
            console.log("原始数据： ", categorys.value);

            level1.value = [];
            level1.value = Tool.array2Tree(categorys.value,0);
            console.log("树形结构： ",level1);

            // 重置分页按钮
          } else {
            message.error(data.message);
          }
        })
      };


      // -------- 表单 ---------
      /**
       * 数组，[100, 101]对应：前端开发 / Vue
       */
          // const categoryIds = ref();
      const category = ref();
      const modalVisible = ref(false);
      const modalLoading = ref(false);
      const handleModalOk = () => {
        modalLoading.value = true;
        // category.value.category1Id = categoryIds.value[0];
        // category.value.category2Id = categoryIds.value[1]

        axios.post("/category/save", category.value).then((response) => {
          modalLoading.value = false; // 只要又返回，就停止loading
          const data = response.data;

          if (data.success) {
            modalVisible.value = false;
            // modalLoading.value = false;
            handleQuery();
          } else {
            message.error(data.message);
          }
          // reload category list


        })
      };

      /**
       * 编辑
       */
      const edit = (record: any) => {
        modalVisible.value = true;
        category.value = Tool.copy(record)
        //category.value = Tool.copy(record);
        //categoryIds.value = [category.value.category1Id, category.value.category2Id]
      };

      /**
       * 新增
       */
      const add = () => {
        modalVisible.value = true;
        category.value = {};
      };


      /**
       * 删除
       */
      const handleDel = (id: number) => {
        axios.delete("/category/delete/" + id).then((response) => {
          const data = response.data
          //if (data.success) {
          //};
          // reload category list
          handleQuery();
        })
      };

      const confirm = (id: number) => {
        handleDel(id);
        handleQuery();
      };

      const cancel = (id: number) => {
        handleQuery();
      };

      const onSearch = (search_value: any) => {
        handleQuery();
      }

      onMounted(() => {
        handleQuery();
      });

      return {
        param,
        // categorys,
        level1,
        columns,
        loading,
        search_value,

        edit,
        add,
        handleDel,

        category,
        modalLoading,
        modalVisible,
        handleModalOk,
        confirm,
        cancel,
        onSearch,
        handleQuery,

      }
    }
  });

</script>