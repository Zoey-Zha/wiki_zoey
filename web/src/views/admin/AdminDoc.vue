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

<!--      <template>-->
<!--        <a-tooltip>-->
<!--          <template #title>prompt text</template>-->
<!--          EbookName is-->
<!--        </a-tooltip>-->
<!--      </template>-->
<!--      <div :style="wrapStyles">-->
<!--        <a-tooltip placement="left" title="Prompt Text" :get-popup-container="getPopupContainer">-->
<!--          <a-button>EbookName-> {{ebookName}}}</a-button>-->
<!--        </a-tooltip>-->
<!--        <br />-->
<!--      </div>-->
      <a-descriptions :title="'EbookName:  ' + ebookName">
      </a-descriptions>
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
                title="Are you sure delete this Doc?"
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
    title="文档结构"
    v-model:visible="modalVisible"
    :confirm-loading="modalLoading"
    @ok="handleModalOk"
  >
    <a-form :model="doc" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
      <a-form-item label="Name">
        <a-input v-model:value="doc.name" />
      </a-form-item>
      <a-form-item label="Parent">
        <a-tree-select
            v-model:value="doc.parent"
            style="width: 100%"
            :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
            :tree-data="treeSelectData"
            placeholder="Please select parent"
            tree-default-expand-all
            :replaceFields="{title: 'name', key: 'id', value: 'id'}"
        >
        </a-tree-select>
      </a-form-item>
      <a-form-item label="Sort">
        <a-input v-model:value="doc.sort" type="textarea" />
      </a-form-item>
    </a-form>
  </a-modal>

</template>
<script lang = "ts">
  import {defineComponent, onMounted,ref} from 'vue';
  import axios from "axios";
  import {message} from "ant-design-vue";
  import {Tool} from "@/util/tool";
  import {useRoute} from "vue-router";

  export default defineComponent({
    name: 'AdminDoc',
    setup: function () {
      // 路由内置的变量
      const route = useRoute();
      console.log("路由： ", route);
      console.log("route.path: ", route.path);
      console.log("route.query: ", route.query); // 目前我们用的这个
      console.log("route.params: ", route.params);
      console.log("route.fullPath: ", route.fullPath);
      console.log("route.meta: ", route.meta);

      const param = ref();
      param.value = {}
      const docs = ref();
      const level1 = ref();
      const search_value = ref();
      const ebookName = route.query.EbookName;
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
        axios.get("/doc/all").then((response) => {
          loading.value = false;
          const data = response.data;
          if (data.success) {
            docs.value = data.content;
            console.log("原始数据： ", docs.value);

            level1.value = [];
            level1.value = Tool.array2Tree(docs.value,0);
            console.log("树形结构： ",level1);

            // 重置分页按钮
          } else {
            message.error(data.message);
          }
        })
      };


      const treeSelectData = ref();
      treeSelectData.value = [];
      const doc = ref();
      const modalVisible = ref(false);
      const modalLoading = ref(false);
      const handleModalOk = () => {
        modalLoading.value = true;
        // doc.value.doc1Id = docIds.value[0];
        // doc.value.doc2Id = docIds.value[1]

        axios.post("/doc/save", doc.value).then((response) => {
          modalLoading.value = false; // 只要又返回，就停止loading
          const data = response.data;

          if (data.success) {
            modalVisible.value = false;
            // modalLoading.value = false;
            handleQuery();
          } else {
            message.error(data.message);
          }
          // reload doc list


        })
      };

      const setDisable = (treeSelectData: any, id: any) => {
        // console.log(treeSelectData, id);
        // 遍历数组，即遍历某一层节点
        for (let i = 0; i < treeSelectData.length; i++) {
          const node = treeSelectData[i];
          if (node.id === id) {
            // 如果当前节点就是目标节点
            console.log("disabled", node);
            // 将目标节点设置为disabled
            node.disabled = true;

            // 遍历所有子节点，将所有子节点全部都加上disabled
            const children = node.children;
            if (Tool.isNotEmpty(children)) {
              for (let j = 0; j < children.length; j++) {
                setDisable(children, children[j].id)
              }
            }
          } else {
            // 如果当前节点不是目标节点，则到其子节点再找找看。
            const children = node.children;
            if (Tool.isNotEmpty(children)) {
              setDisable(children, id);
            }
          }
        }
      };

      /**
       * 编辑
       */
      const edit = (record: any) => {
        modalVisible.value = true;
        doc.value = Tool.copy(record)

        //不能选择当前节点及其所有子孙节点
        treeSelectData.value = Tool.copy(level1.value);
        setDisable(treeSelectData.value, record.id);

        // 为选择树添加一个"无",前面加一个无
        treeSelectData.value.unshift({id: 0, name: 'Root'});

        //doc.value = Tool.copy(record);
        //docIds.value = [doc.value.doc1Id, doc.value.doc2Id]
      };

      /**
       * 新增
       */
      const add = () => {
        modalVisible.value = true;
        doc.value = {
          ebookId: route.query.EbookId
        };

        treeSelectData.value = Tool.copy(level1.value);
        // setDisable(treeSelectData.value, record.id);

        // 为选择树添加一个"无",前面加一个无
        treeSelectData.value.unshift({id: 0, name: 'Root'});

      };


      /**
       * 删除
       */
      const handleDel = (id: number) => {
        axios.delete("/doc/delete/" + id).then((response) => {
          const data = response.data
          //if (data.success) {
          //};
          // reload doc list
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
        // docs,
        level1,
        columns,
        loading,
        search_value,
        treeSelectData,
        ebookName,

        edit,
        add,
        handleDel,

        doc,
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