<template>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <p>
        <a-button type="primary" @click="add" size = "large">
          New
        </a-button>
      </p>
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
            <a-popconfirm
                title="Are you sure delete this Ebook?"
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
      <a-form-item label="分类1">
        <a-input v-model:value="ebook.category1Id" />
<!--        <a-cascader-->
<!--            v-model:value="categoryIds"-->
<!--            :field-names="{ label: 'name', value: 'id', children: 'children' }"-->
<!--            :options="level1"-->
<!--        />-->
      </a-form-item>
      <a-form-item label="分类2">
        <a-input v-model:value="ebook.category2Id" type="textarea" />
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
  import {message} from "ant-design-vue";

  export default defineComponent({
    name: 'AdminEbook',
    setup() {
      const ebooks = ref();
      const pagination = ref({
        current: 1,
        pageSize: 10,
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
          title: 'ViewNum',
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
          if(data.success) {
            ebooks.value = data.content.list;
            // 重置分页按钮
            pagination.value.current = p.page;  // 注释掉后发现，翻页失效
            pagination.value.total = data.content.totalNum;
          } else {
            message.error(data.message);
          }
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
        // ebook.value.category2Id = categoryIds.value[1]

        axios.post("/ebook/save",ebook.value).then((response) => {
          modalLoading.value = false; // 只要又返回，就停止loading
          const data = response.data;

          if (data.success) {
            modalVisible.value = false;
            // modalLoading.value = false;
            handleQuery({
              page: pagination.value.current,
              size: pagination.value.pageSize
            });
          } else {
            message.error(data.message);
          }
          // reload ebook list


        })
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

      /**
       * 新增
       */
      const add = () => {
        modalVisible.value = true;
        ebook.value={};
      };


      /**
       * 删除
       */
      const handleDel = (id: number) => {
        axios.delete("/ebook/delete/"+id).then((response) => {
          const data = response.data;

          //if (data.success) {
          //};
          // reload ebook list
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize
          });
        })
      };

      const confirm = (id: number) =>{
        handleDel(id);
        handleQuery({
          page: pagination.value.current,
          size: pagination.value.pageSize
        });
      };

      const cancel = (id: number) =>{
        handleQuery({
          page: pagination.value.current,
          size: pagination.value.pageSize
        });
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
        add,
        handleDel,

        ebook,
        modalLoading,
        modalVisible,
        handleModalOk,
        confirm,
        cancel,

      }
    }
  });

</script>