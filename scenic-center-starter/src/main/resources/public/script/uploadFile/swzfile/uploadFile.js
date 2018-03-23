//flash上传文件，支持跨域
var uploadFileIndex_ = 0;
$.fn.extend({
	uploadFile : function(options) {
		// 默认参数
		var settings = {
			url : "http://filealiyun.geeker.com.cn/uploadOSS?path=test",// 数据获取url
			policyFile:'http://filealiyun.geeker.com.cn/crossdomain.xml',//跨域验证文件
			swfPath:"../js/common/uploadFile/swzfile/uploadFile.swf", //falsh文件路径
			width:"auto",//flash宽度 auto等于控件的宽度
			height:"auto",//flash高度 auto等于控件的高度
			
			fileFilter:'*.*;', //文件选择过滤器 多个用;隔开
			requestMethod:'post', //url请求方式
			data:{}, //额外参数
			multi:false, //是否多文件上传
			isAlert:false, //flash内部提示信息
			isUrlTest:false, //flash打印请求url
			
			isProgress:false,
			progressWidth:500,//进度条宽度
			progressArea:'',//进度条显示区域（jq选择器）
			fadetime:2000,//进度条消失动画时间 单位毫秒
			minSize:0, //单个文件最小限制(单位B 0表示不限制)
			maxSize:0, //单个文件最大限制(单位B 0表示不限制)
			maxFileCount:0, //最大文件数 0表示不限制
			
			filetag:"file", //文件内容变量名 ,不填或者为空为默认的 FileData 阿里OSS的标志是file
			isOssService:true, // 是否是阿里云oss服务，阿里云OSS服务单独处理 ,当设置为true时，url参数无效，不在使用
			ossDomain: "http://file.geeker.com.cn", //与阿里云oss绑定的域名
			ossSignatureUrl:"http://file.geeker.com.cn/getSignature", //阿里云签名获取URL 
			ossPathSffix:"default", //阿里云文件上传自定义路径
			
			
			mouseover:function(jq){ //当鼠标移入时  jq:触发的jq对象
			},
			mouseout:function(jq){ //当鼠标移除时 jq:触发的jq对象
			},
			onFileError:function(jq,settings,data,errorCode){ //当发生选择文件错误时
				 if(errorCode == "toomore"){
					 alert("文件数超过最大限制");
				 }else if(errorCode == "toosmall"){
					 alert("文件["+data.name+"]大小不满足最小值设定");
				 }else  if(errorCode == "toolarge"){
					 alert("文件["+data.name+"]大小不满足最大值设定");
				 }else  if(errorCode == "noimage"){  
					 alert("文件["+data.name+"]无法识别的图片格式]");
				 }else{
					 alert("文件["+data.name+"]检查其他错误["+errorCode+"]");
				 }
			},
			onComplete:function(jq,settings,data){ //上传完成回调函数 jq:触发的jq对象  上传成功的定义只表示服务器接受信息后做出respone响应，正真的成功与否还需要根据respone内容做进一步判断
				//console.info(data);
			},
			onError:function(jq,settings,data){ //上传失败回调函数 jq:触发的jq对象
				//console.info(data);
			},
			onUploading:function(jq,settings,data){ //上传前回调函数
				
			},
			onProgress:function(jq,settings,data){ //上传进度
				
				
				var fileName = data.name;
				var fileSize = data.size;
				var fileTotal = data.allFileCount;
				var fileIndex = data.fileIndex;
				var curSize = data.curSize;
				
				var vesselid = settings.vesselid;
				var $div = $("#progress_"+vesselid)
				var $div2 = null;
				var $div4 = null;
				if($div.size() > 0){
					$div4 = $div.find(".uploadProgress-bar");
				}else{
					$div = $("<div class='uploadDiv'></div>");
					$div.attr("id","progress_"+settings.vesselid);
					$div.css("width",settings.progressWidth+"px");
					var $div2 = $("<div class='uploadDiv'></div>");
					$div2.attr("index",fileIndex);
					$div2.append(fileName);
					$div2.append(" ");
					$div2.append("("+fileSizeFormat(fileSize)+")");
					$div2.append("<b class='uploadB'></b>");
					
					var $div3 = $("<div class='uploadDiv uploadProgress'></div>");
					var $div4 = $("<div class='uploadDiv uploadProgress-bar'></div>");
					$div3.append($div4);
					$div2.append($div3);
					$div.append($div2);
					
					var $progressArea = null; 
					if(settings.progressArea && settings.progressArea != ""){
						$progressArea = $(settings.progressArea);
					}
					
					if($progressArea && $progressArea.size() > 0 ){ //区域存在
						$("#aaa").append($div);
					}else{ //区域不存在
						//弹层显示
						
						var $opacity = $('<div class="uploadOpacity" style="width: 100%; height:100%;"></div>');
						$opacity.attr("id","progress_opacity_"+settings.vesselid);
						$("body").append($opacity);
						
						$div.addClass("uploadPop");
						$div.css("margin-left",(-1)*settings.progressWidth/2+"px");
						$div.css("margin-top","-10px");
						$("body").append($div);
					}
					
					
				}
				var progress = new Number((curSize/fileSize)*100);
				if(progress > 100){
					progress = 100;
				}
				progress = progress.toFixed(2)+"%";
				$div4.css("width",progress)
				$div4.text(progress);
				
				
				
			},
			onRepeatUpload:function(jq,settings,data){
				alert("文件正在上传中,请稍后再试!");
			}
		};
		$.extend(settings, options);
		
		for(var i = 0 ;i < $(this).size(); i++){
			var $this = $(this).eq(i);
			//产生一个身份标识
			var identify = new Date().getTime();
			settings.vesselid = identify;
			settings.identify = identify+"_"+uploadFileIndex_;
			uploadFileIndex_++;
			//debugger;
			loadSwf($this,settings);
		}
	}
});

//装载swf文件
function loadSwf($this,settings){
	
	  //产生div装载flash
	  var $div = $("<span></span>");
	  var width ;
	  var height;
	  if(settings.width == "auto"){
		width = $this.outerWidth();
	  }else{
		width = settings.width;
	  }
	  $div.width(width);
	  
	  if(settings.height == "auto"){
	  	height = $this.outerHeight();
	  }else{
	  	height = settings.height;
	  }
	  $div.height(height);
	  //位置定位,跟初始化flash的控件在同一位置
	  //$div.css("position","absolute");
	 // var offset = $this.offset();
	  //$div.css("top",offset.top);
	  //$div.css("left",offset.left);
	  $div.css("display","inline-block");
	  $div.css("margin-left",(-1*width)+"px");
	  $div.css("z-index",99);
	  $div.css("vertical-align","middle");
	 // $span.css("vertical-align","middle");
	  
	  $div.hover(function(){
		  if(settings.mouseover){
			  settings.mouseover($this);
		  }
	  },function(){
		  if(settings.mouseout){
			  settings.mouseout($this);
		  }
	  })
	  
	  var $objectdiv = $("<div></div>");
	  var vesselid = "swf_vessel_"+settings.identify;
	  $objectdiv.attr("id",vesselid);
	  $div.data("data",settings);
	  $div.append($objectdiv);
	  
	  
	  var $span = $("<span ></span>");
	  //$this.after($div); 
	  $span.css("display","inline-block");
	  
	 
	  $this.after($span); 
	  $span.append($this);
	  $span.append($div);
	  
	  
	  
	  var data = null;
	  if(settings.data != null){
	  	data = $.param(settings.data);
	  }
	  
	  //falsh请求参数
	  var params = {
			  vesselid:vesselid, //容器ID,jsFunction时返回该参数
			  uploadServerUrl : settings.url,	//上传响应页面(必须设置)
			  uploadSuccessCallback : "uploadSuccessCallback",			//上传成功后回调JS
			  uploadErrorCallback : "uploadErrorCallback",			//上传失败后回调js
			  uploadingCallback:"uploadingCallback",
			  repeatUploadCallback:"repeatUploadCallback",
			  fileErrorCallback:"fileErrorCallback",
			  progressCallback:settings.isProgress?"progressCallback":null,
			  policyFile : settings.policyFile,
			  wmode:"transparent",
			  requestMethod:settings.requestMethod, 
			  multi:settings.multi,
			  data:data,
			  filter :settings.fileFilter,		//上传文件类型限制
			  minSize:settings.minSize,
			  maxSize:settings.maxSize,
			  maxFileCount:settings.maxFileCount,
			  filetag:settings.filetag,
			  
			  isOssService:settings.isOssService, 
			  ossDomain: settings.ossDomain, //与阿里云oss绑定的域名
			  ossSignatureUrl:settings.ossSignatureUrl, //阿里云签名获取URL 
			  ossPathSffix:settings.ossPathSffix, //阿里云oss文件上传自定义路径
			  
			  isAlert:false  //上传完毕后flash不提示 上传成功
			  
		 }
	  
		var swfParams = {
			 wmode:"transparent"
		}
		
		if(typeof(swfobject) == "undefined"){
			$("body").append('<script type="text/javascript" src="../js/common/uploadFile/swfobject.js"></script>');
		}
	    swfobject.embedSWF(settings.swfPath, vesselid, "100%", "100%", "10.0.0", "flash/expressInstall.swf", params,swfParams);
}

//上传成功后的回调函数
//params flash参数
//responseStr后响应的内容
function uploadSuccessCallback(params,responseStr){
	
	var vesselid = params.vesselid;
	var jq = $("#"+vesselid).parent().prev();  //触发的按钮
	
	var $swfobject = $("#"+vesselid).parent();
	var settings = $swfobject.data("data");
	
	//移除进度条
	removeProgress(settings);
	
	
	if(settings.onComplete){
		settings.onComplete(jq,settings,responseStr);
	}
	return ;
}

//上传失败后的回调函数
//params flash参数
//responseStr后响应的错误信息
function uploadErrorCallback(params,responseStr){
	var vesselid = params.vesselid;
	var jq = $("#"+vesselid).parent().prev();  //触发的按钮
	var $swfobject = $("#"+vesselid).parent();
	var settings = $swfobject.data("data");
	
	//移除进度条
	removeProgress(settings);
	
	
	if(settings.onError){
		settings.onError(jq,settings,responseStr);
	}
	return ;
}

function uploadingCallback(params){
	
	var vesselid = params.vesselid;
	var jq = $("#"+vesselid).parent().prev();  //触发的按钮
	var $swfobject = $("#"+vesselid).parent();
	var settings = $swfobject.data("data");
	if(settings.onUploading){
		settings.onUploading(jq,settings);
	}
}

function repeatUploadCallback(params){
	var vesselid = params.vesselid;
	var jq = $("#"+vesselid).parent().prev();  //触发的按钮
	var $swfobject = $("#"+vesselid).parent();
	var settings = $swfobject.data("data");
	if(settings.onRepeatUpload){
		settings.onRepeatUpload(jq,settings);
	}else{
		Alert("图片上传中,请等待上传完毕后再执行!");
	}
}

function progressCallback(params,fileData){
	var vesselid = params.vesselid;
	var jq = $("#"+vesselid).parent().prev();  //触发的按钮
	var $swfobject = $("#"+vesselid).parent();
	var settings = $swfobject.data("data");
	if(settings.isProgress){
		settings.onProgress(jq,settings,fileData);
	}
}


function fileErrorCallback(params,fileData,errorCode){
	var vesselid = params.vesselid;
	var jq = $("#"+vesselid).parent().prev();  //触发的按钮
	var $swfobject = $("#"+vesselid).parent();
	var settings = $swfobject.data("data");
	if(settings.onFileError){
		settings.onFileError(jq,settings,fileData,errorCode);
	}
	
}


function removeProgress(settings){
	if(settings.isProgress){
		
		var fadetime = settings.fadetime;
		
		var vesselid = settings.vesselid;
		var $div = $("#progress_"+vesselid)
		$div.fadeOut(fadetime,function(){
			$(this).remove();
		});
		
		var $progressDiv = $("#progress_opacity_"+vesselid) 
		$progressDiv.fadeOut(fadetime,function(){
			$(this).remove();
		});
	}
}


function fileSizeFormat(value){
	
	var strReturn = "";
	
	if (value < 1024){
		strReturn += value + " B";
	}else if (value < 1024 * 1024){
		value = value / 1024;
		strReturn += formatNumber(value,2) + " K";
	}else if (value < 1024 * 1024 * 1024){
		value = value / 1024 / 1024;
		strReturn += formatNumber(value,2) + " M";
	}else{
		value = value / 1024 / 1024 / 1024;
		strReturn += formatNumber(value,2) + " G";
	}
	
	return strReturn;
}

function formatNumber(value,formatNumber){
	return new Number(value).toFixed(formatNumber);
}

