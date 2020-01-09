/**
* create by zx on 2018/11/15 16:51
* 类注释：
* 备注：
*/
<template>
  <div class="pdf">
    <div class="container">
      <div v-for="(item,index) in pageNum">
        <canvas class="pdf_c" :id="index+1"></canvas>
      </div>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  import PDFJS from 'pdfjs-dist'

  export default {
    name: "pdf",
    components: {},
    props: {
      pdfUrl: {
        type: String,
        default() {
          return "";
        }
      }
    },
    data() {
      return {
        pdfDoc: null,
        pageNum: 1,
        scale: 1.2,
      }
    },
    watch: {
      pdfUrl(val) {
        this.pdfUrl = val;
        this.showPDF(this.pdfUrl);
      }
    },
    computed: {},
    methods: {
      showPDF(url) {
        console.log("url:" + url)
        if (!isEmptyObject(url)) {
          this.showLoading();
          let _this = this;
          PDFJS.getDocument(url).then(function (pdf) {
            _this.pdfDoc = pdf;
            _this.pageNum = pdf._pdfInfo.numPages;
            for (let i = 0; i < _this.pageNum; i++) {
              _this.renderPage(i + 1)
            }
          }).finally(()=>{
            _this.hideLoading();
          })
        }
      },
      //分页加载
      renderPage(num) {
        console.log(num);
        let _this = this;
        this.pdfDoc.getPage(num).then(function (page) {
          var viewport = page.getViewport(_this.scale);
          let canvas = document.getElementById(num);
          canvas.height = viewport.height;
          canvas.width = viewport.width;
          var renderContext = {
            canvasContext: canvas.getContext('2d'),
            viewport: viewport
          };
          var renderTask = page.render(renderContext);
          renderTask.promise.then(function () {

          })
        })
      },
    },
    activated() {
    },
    mounted() {
    },
  }
</script>

<style scoped lang="less" rel="stylesheet/less">
  .pdf {
    .container {
      width: 100%;
      height: 100%;
      text-align: center;
      .pdf_c {
        width: 100%;
        height: 100%;
      }
    }
  }
</style>
