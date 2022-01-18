<template>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <a-table
          :columns="columns"
          :row-key="record => record.id"
          :data-source="ebooks"
          :pagination="pagination"
          :loading="loading"
          @change="handleTableChange"
      >
        <template #cover="{ text: cover}">
          <img v-if="cover" :src="cover"  :alt = "avatar"/>
        </template>
        <template v-slot:action="{ text, record }">
          <a-space size="small">
            <a-button type="primary" @click="edit(record)">
              Edit
            </a-button>
            <a-button type="danger">
              Remove
            </a-button>
          </a-space>
        </template>
      </a-table>
    </a-layout-content>
  </a-layout>
  <a-modal
    title="电子书表单"
    v-model:visible="modalVisible"
    :confirm-loading="modalLoading"
    @ok="handleModalOk"
  >
    <a-form :model="ebook" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
      <a-form-item label="封面">
        <a-input v-model:value="ebook.cover" />
      </a-form-item>
      <a-form-item label="名称">
        <a-input v-model:value="ebook.name" />
      </a-form-item>
      <a-form-item label="分类">
        <a-cascader
            v-model:value="categoryIds"
            :field-names="{ label: 'name', value: 'id', children: 'children' }"
            :options="level1"
        />
      </a-form-item>
      <a-form-item label="描述">
        <a-input v-model:value="ebook.description" type="textarea" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script lang = "ts">
  import {defineComponent, onMounted,ref} from 'vue';
  import axios from "axios";

  export default defineComponent({
    name: 'AdminEbook',
    setup() {
      const ebooks = ref();
      const pagination = ref({
        current: 1,
        pageSize: 3,
        total: 0
      });

      const loading = ref(false);
      const columns = [
        {
          title: 'Cover',
          dataIndex: 'cover',  // 数据源吧，把查出结果的列名匹配上
          slots: { customRender: 'cover'}
        },
        {
          title: 'Name',
          dataIndex: 'name',
        },
        {
          title: 'Category1',
          key: 'category1Id',
          dataIndex: 'category1Id',
        },
        {
          title: 'Category2',
          key: 'category2',
          dataIndex: 'category2Id',
        },
        {
          title: 'ReadNum',
          dataIndex: 'viewCount',
        },
        {
          title: 'LikedNum',
          dataIndex: 'voteCount',
        },
        {
          title: 'Action',
          dataIndex: 'action',
          slots: { customRender: 'action' }
        }
      ];

      /**
       *  数据查询
       */
      const handleQuery = (p: any) => {
        loading.value = true;
        axios.get("/ebook/ebookList",{
          params: {
            page: p.page,
            size: p.size
          }
        }).then((response) => {
          loading.value = false;
          const data = response.data;
          ebooks.value = data.content.list;

          // 重置分页按钮
          pagination.value.current = p.page;  // 注释掉后发现，翻页失效
          pagination.value.total = data.content.totalNum;
        })
      };

      /**
       * 表格点击页码时触发
       */
      const handleTableChange = (pagination: any) => {
        console.log("Look, 自带的分页参数" + pagination);
        handleQuery({
          page: pagination.current,
          size: pagination.pageSize
        });
      };

      // -------- 表单 ---------
      /**
       * 数组，[100, 101]对应：前端开发 / Vue
       */
      // const categoryIds = ref();
      const ebook = ref();
      const modalVisible = ref(false);
      const modalLoading = ref(false);
      const handleModalOk = () => {
        modalLoading.value = true;
        // ebook.value.category1Id = categoryIds.value[0];
        // ebook.value.category2Id = categoryIds.value[1];
        setTimeout(() => {
          modalVisible.value = false;
          modalLoading.value = false;
        },2000);
      };

      /**
       * 编辑
       */
      const edit = (record: any) => {
        modalVisible.value = true;
        ebook.value=record
        //ebook.value = Tool.copy(record);
        //categoryIds.value = [ebook.value.category1Id, ebook.value.category2Id]
      };


      onMounted(() => {
        handleQuery({
          page: 1,
          size: pagination.value.pageSize
        });
      });

      return {
        ebooks,
        pagination,
        columns,
        loading,
        handleTableChange,

        edit,

        ebook,
        modalLoading,
        modalVisible,
        handleModalOk
      }
    }
  });

</script>