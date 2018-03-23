function replace(str) {
    return str.replace(/[\(（]([a-zA-Z\s]{1,6})[\)）]/g, '[$1]').replace(/(\[\s{0,3})/g, "[").replace(/(\s{0,3}\])/g, "]");
}

var exam = {
    questionCount:0,
    //初始化
    init: function () {
        //拖拽
        this.dragFn();
        //拖拽排序
        this.sortFn();
        //题目菜单滚动固定顶部
        this.fixFn();
        //题目菜单折叠/展开
        this.menuFn();
        //标题编辑
        this.titleEditFn();
        //题操作事件初始化
        this.listAllCtrlFn('.ui-questions-content-list', '.ui-up-btn', '.ui-down-btn', '.ui-clone-btn', '.ui-del-btn');
        //批量添加事件初始化
        this.topicACtrlFn('.ui-questions-content-list', '.ui-add-item-btn', '.ui-batch-item-btn', '.ui-add-answer-btn');
        //
        this.moveTispFn('.ui-up-btn,.ui-down-btn,.ui-clone-btn,.ui-del-btn');
        this.moveTispFn('.ui-add-item-btn,.ui-batch-item-btn,.ui-add-answer-btn');
        this.submitData();

    },
    //拖拽
    dragFn: function () {
        var _this = this;
        var data = {}, addname = _this.questionCount;
        $("#ui_sortable_exam li").draggable({
            /* containment:'#pageContentId',*/
            connectToSortable: '.ui-questions-content-list',
            cursorAt: {top: 18, left: 20},
            helper: function (event) {
                addname++;
                data = {
                    type: $(this).children('a').attr('data-checkType'),//1为单选，2为多选
                    name: 'Q' + $(this).attr('data-uid') + '_' + addname,
                    //生成1000以内的随机数
                    itmetid: addname + parseInt(1000 * Math.random()),
                    items: [{
                        value: '0',
                        //生成1000以内的随机数
                        tid: addname + parseInt(1000 * Math.random())
                    }, {
                        value: '0',
                        //生成1000以内的随机数
                        tid: addname + parseInt(1000 * Math.random())
                    }]
                }
                return template($(this).attr('data-tempId'), data);
            },
            revert: 'invalid',
            start: function (event) {
                _this.titleDelFn();
            },
            drag: function (event) {

            },
            stop: function (event) {
                _this.orderFn($('.ui-questions-content-list'));
            }
        }).on('click', function (e) {
            addname++;
            var type = $(this).children('a').attr('data-checkType'),title = '';
            title = (type=='single_select')?'单选题目标题':'多选题目标题' ;
            (type.indexOf('select')==-1)&&(title = (type=='single_answer')?'无规则题目标题':'有规则题目标题');
            var notice = (type.indexOf('select')==-1) ? '子题':'选项';
            data = {
                type: type,//试题类型
                //name命名规则，q代表前缀+父级li的题型id
                name: 'Q' + addname,
                title: title,
                inputRule:'', //输入类型限制,仅对填空题有效；
                //生成1000以内的随机数
                itemtid: addname + parseInt(10000* Math.random()),
                items: [{
                    id: 'Q' + addname +'_'+1,
                    //生成1000以内的随机数
                    question:notice+'1',
                    tid:addname+parseInt(10000*Math.random()),
                    redirectNum:'',
                    inputRule:''
                }, {
                    id: 'Q' + addname +'_'+2,
                    //生成1000以内的随机数
                    question:notice+'2',
                    tid:addname+parseInt(10000*Math.random()),
                    redirectNum:'',
                    inputRule:''
                }]
            }
            $('.ui-questions-content-list').append(template($(this).attr('data-tempId'), data));
            _this.orderFn($('.ui-questions-content-list'));
            _this.sortFn();
        }).disableSelection();
    },
    initData: function (data) {
        var _this = this;
        $('.questions-head-title h4 span').text(data.examTitle);
        ($('.pagebox').css({display:'block'}));
        data.questionItems.forEach(function (t) {
            $('.ui-questions-content-list').append(template(t.tempId, t));
            if(t.tempId==='drag_describe'){
                t.additionStr = t.additionStr.replace(/[;；](?=\d)/g,';<br/>');
                $('.ui-questions-content-list').find('li:last').find('.cq-items-content span').html(t.additionStr);
            }
        });

        _this.orderFn($('.ui-questions-content-list'));
        _this.sortFn();
    },
    submitData: function () {
        var _this = this;
        $('#saveQuestion').on('click', function () {
            _this.saveData();
        });
    },
    saveData: function () {
        var dataBase = {}, questionItems = [],reg = /[\(（]([a-zA-Z\s]{1,6})[）\)]/,flag= true;
        dataBase.examTitle = $('#questionTitle span').text();

        //封装所有题列表，遍历提取值analysis（答案）、题列表（数组对象）；
        $('.ui-questions-content-list').children('li').each(function (i) {
            if(!flag){
                return ;
            }
            var dataTx = {}, qListItems = [];
            var type = ($(this).find('.theme-type').attr('data-type'));
            dataTx.tempId =($(this).find('.theme-type').attr('data-temp'));
            dataTx.name = $(this).find('.ui-drag-area').attr('data-nameStr');
            dataTx.itemtid = $(this).find('.cq-title').attr('data-tid');
            dataTx.inputRule = $(this).find('.cq-title').attr('data-tag');
            dataTx.title = $(this).find('.cq-title span').text();
            //封装单题，遍历提取值name、value、checkCurr（选中状态）；
            if (type.indexOf('select') > 0) {
                var ans = [];
                $(this).find('ul.cq-unset-list').children('li').each(function (j) {
                    var listItems = {};
                    if(!flag){
                        return;
                    }
                    listItems.id = $(this).find('input').attr('data-seq');
                    listItems.tid = $(this).find('.cq-answer-content').attr('data-tid');
                    listItems.question = $(this).find('.cq-answer-content').text();
                    ($(this).find('input').prop('checked')) && (ans.push(listItems.id));
                    (type.indexOf('single')>-1)&&(listItems.redirectNum =$(this).find('.redirect-num').val());
                    qListItems.push(listItems);
                });
            }else if(type==='rule_answer'){
                if(!flag){
                    return ;
                }
                if(!reg.test(dataTx.title)){
                    alert('输入的填空项需包含（字母）所示的格式');
                    $(this).focus();
                    flag= false;
                    return ;
                };
                dataTx.resTitle = replace(dataTx.title);
                $(this).find('ul.cq-unset-list').children('li').each(function (j) {
                    var listItems = {};
                    listItems.id = $(this).find('.input_completion').text();
                    listItems.tid = $(this).find('.cq-answer-content').attr('data-tid');
                    listItems.inputRule = $(this).find('.cq-answer-content').attr('data-tag');
                    listItems.question = $(this).find('.cq-answer-content').text();
                    if(!reg.test(listItems.question)){
                       console.log(listItems.question)
                        alert('输入的填空项需包含（字母）所示的格式');
                        $(this).focus();
                        flag= false;
                        return ;
                    };
                    listItems.ruleQuestion = replace(listItems.question);
                    qListItems.push(listItems);
                });
                dataTx.rule = $(this).find('.ui-rule-area .cq-title').text();
            }else{
                if(!reg.test(dataTx.title)){
                    alert('输入的填空项需包含（字母）所示的格式');
                    $(this).focus();
                    flag= false;
                    return ;
                };
                dataTx.resTitle = replace(dataTx.title);
                /*if (type.indexOf('single') === -1)  {  //有规则填空
                    dataTx.additionStr = $(this).find('.cq-items-content .describe-edit-content').text(); //取出所有子题
                    var childStr = replace(dataTx.additionStr);
                    var childItem = childStr.split(/[;；]\d[:：]/);
                    qListItems = childItem.map(function (t,index) {
                        var item = {};
                        item.id = index+1;
                        item.tid = $(this).find('.describe-edit-content').attr('data-tid');
                        if((/[;；]\d\.\d[:：]/.test(t))){
                            var childItem = t.split(/[;；]\d\.\d[:：]/);
                            t = childItem.shift()+';'; //弹出父元素；
                            item.items = childItem.map(function (t2,index) {
                                var item = {};
                                item.id = index +1 ;
                                item.question = t2;
                                return item;
                            })
                        }
                        item.question = t;
                        return item;
                    })
                    dataTx.rule = $(this).find('.ui-rule-area .question-rule').text();
                  }*/
            }
            dataTx.type = type;
            dataTx.answer = ans;
            dataTx.items = qListItems;
            questionItems.push(dataTx);
        });
        dataBase.questionItems = questionItems;
        if(!flag){
            return ;
        }
        localStorage.setItem('examdata', JSON.stringify(dataBase))
        console.log(dataBase);
    },
    //拖拽排序
    sortFn: function () {
        var _this = this;
        $('.ui-questions-content-list').sortable({
            handle: '.ui-drag-area',
            items: '>li',
            containment: '#pageContentId',
            opacity: 0.7,
            placeholder: 'ui-state-highlight',
            start: function (event) {
                exam.titleDelFn();
            },
            stop: function () {
                _this.orderFn($(this));
            },
            revert: 'invalid'
        });
    },
    //标题序列号
    orderFn: function (obj) {
        obj.find('li.items-questions').each(function (i) {
            var sequence = "Q" + (i + 1);
            $(this).find('.module-menu h4').html(sequence.toUpperCase());  //修改显示题号
            $(this).find(".cq-unset-list").attr('data-namestr', sequence)  ; //修改隐藏题号
            $(this).find(".ui-drag-area").attr('data-namestr', sequence);
            $(this).find(".cq-unset-list .input-check input").each(function (i) {
                $(this).attr('name', sequence);
            })
            $(this).find(".cq-unset-list .input-check input").each(function (i) {
                $(this).attr('data-seq', sequence + '_' + (i + 1));
            })
            $(this).find(".cq-unset-list .input_completion").each(function (i) {
                $(this).text(sequence + '_' + (i + 1));
            })
        });
    },
    //题目菜单滚动固定顶部
    fixFn: function () {
        var _this = this;
        $('#desktop_scroll').scroll(function () {
            _this.titleDelFn();
            var parentLeft = $('.exam-nav').parent().offset().left;
            if ($('.exam-nav').offset().top + 20 + $('.conditionItems').outerHeight() + $('.title').outerHeight() <= $(this).scrollTop()) {
                $('.exam-nav').css({'position': 'fixed', 'top': 0 + 'px', 'left': parentLeft + 'px'});
                $('.exam-nav').addClass('scrollCurr');
            } else {
                $('.exam-nav').removeAttr('style');
                $('.exam-nav').removeClass('scrollCurr');
            }

        });
    },
    //题目菜单折叠/展开
    menuFn: function () {
        $('.exam-item-title').on('click', function () {
            if ($(this).hasClass('curr')) {
                $(this).removeClass('curr');
                $(this).find('i').removeClass('icon-collapse').addClass('icon-expand');
                $(this).next('ul.exam-nav-list').stop().slideDown();
            } else {
                $(this).addClass('curr');
                $(this).find('i').removeClass('icon-expand').addClass('icon-collapse');
                $(this).next('ul.exam-nav-list').stop().slideUp();
            }
        });
    },
    //标题编辑
    titleEditFn: function () {
        $(document).on('click', '.T_edit', function (event) {
            var _this = this;
            $('.cq-into-edit').remove();
            var data = {
                title: ''
            }
            if (!$('.cq-into-edit').size()) {
                $('body').append(template('drag_T_edit', data));
                $('.cq-into-edit').attr('data-gettid', $(this).attr('data-tid'));
            }
            if ($(this).hasClass('T_plugins')) {
                var dataStr = $(this).attr('data-tag');
                var dataid = $(this).attr('data-tid');
               $('.cq-into-edit').append(template('rule_edit_plugins',{id:$(this).attr('data-tid'),tagArr:dataStr}));
            }
            $('.cq-into-edit').css({
                'top': ($(this).offset().top - 1) + 'px',
                'left': ($(this).offset().left) + 'px',
                'width': $(this).outerWidth() + 'px',
            });
            if ($(this).hasClass('T-center')) {
                $('.cq-into-edit .cq-edit-title').css({
                    'text-align': 'center'
                });
            } else {
                $('.cq-into-edit .cq-edit-title').css({
                    'text-align': 'left'
                });
            }
            if ($(this).attr('data-font')) {
                $('.cq-into-edit .cq-edit-title').css({
                    'font-size': $(this).attr('data-font') + 'px'
                });
            } else {
                $('.cq-into-edit .cq-edit-title').css({
                    'font-size': ''
                });
            }
            $('.cq-into-edit .cq-edit-title').css({
                'min-height': $(this).height() + 'px',
                'padding-top': ($(this).outerHeight() - $(this).height()) / 2 + 'px',
                'padding-bottom': ($(this).outerHeight() - $(this).height()) / 2 + 'px'
            }).html($(this).html()).focus();

            $(document).on('click', function () {
                $('.cq-into-edit').remove();
            });
            $(document).on('click', '.edit-plug-box .reg-data', function (e) {
                var tags = $(_this).attr('data-tag').split(',');
                var hoverTag = $(this).attr('data-type');
                if(tags.indexOf(hoverTag)>-1){  //开始有这个属性，要取消这个属性
                    tags.splice(tags.indexOf(hoverTag),1); //删除这个属性；
                    $(this).removeClass('tag-selected');
                }else{
                    tags.push(hoverTag);
                    $(this).addClass('tag-selected');
                }
                $(_this).attr('data-tag',tags.join(','));
                e.stopPropagation();
            });
            $(document).on('click', '.cq-into-edit', function (e) {
                e.stopPropagation();
            });
            event.stopPropagation();
        });
        $(document).on('blur', '.cq-into-edit .cq-edit-title', function () {
            $('.T_edit[data-tid=' + $('.cq-into-edit').attr('data-gettid') + ']').html($('.cq-into-edit .cq-edit-title').html());
            var $itemInput = $('.T_edit[data-tid=' + $('.cq-into-edit').attr('data-gettid') + ']').closest('li').find('.input-check').find('input');
            if ($itemInput.size()) {
                $itemInput.val($('.cq-into-edit .cq-edit-title').html());
            }
        });
    },
    //关闭标题编辑
    titleDelFn: function () {
        if ($('.cq-into-edit').size()) {
            $('.T_edit[data-tid=' + $('.cq-into-edit').attr('data-gettid') + ']').html($('.cq-into-edit .cq-edit-title').html());
            $('.cq-into-edit').hide();
        }
    },
    //鼠标移动上显示
    moveTispFn: function (obj) {
        $(document).on('mousemove', obj, function (e) {
            var strTx = $(this).attr('data-tisp');
            var str = $('<div class="move-tisp-box"></div>');
            str.html(strTx);
            if (!$('.move-tisp-box').size()) {
                str.appendTo('body');
            }
            $('.move-tisp-box').css({top: (e.pageY + 15) + 'px', left: (e.pageX + 15) + 'px'});
        });
        $(document).on('mouseout', obj, function (e) {
            $('.move-tisp-box').remove();
        });
    },
    //控制操作：上移，下移，复制，删除
    listAllCtrlFn: function (parentObj, upObj, downObj, cloneObj, delObj) {
        var _this = this;
        //上移
        $(document).on('click', parentObj + ' ' + upObj, function (e) {
            var $parentItems = $(this).closest('li.ui-module');
            if ($parentItems.prev('li.ui-module').size()) {
                $parentItems.insertBefore($parentItems.prev('li.ui-module'));
                _this.orderFn($(parentObj));
                _this.titleDelFn();
            } else {
                layer.msg('已经是第一个了！');
            }
        });
        //下移
        $(document).on('click', parentObj + ' ' + downObj, function (e) {
            var $parentItems = $(this).closest('li.ui-module');
            if ($parentItems.next('li.ui-module').size()) {
                $parentItems.insertAfter($parentItems.next('li.ui-module'));
                _this.orderFn($(parentObj));
                _this.titleDelFn();
            } else {
                layer.msg('已经是最后一个了！');
            }
        });
        //复制/克隆
        $(document).on('click', parentObj + ' ' + cloneObj, function (e) {
            var $parentItems = $(this).closest('li.ui-module');
            var newd=$parentItems.clone(true);
            newd.find('.T_edit').each(function (t) {
                $(this).attr('data-tid',parseInt(1000*Math.random())+123);
            });
            newd.insertAfter($parentItems);
            _this.orderFn($(parentObj));
            _this.titleDelFn();
        });
        //删除
        $(document).on('click', parentObj + ' ' + delObj, function (e) {
            var $parentItems = $(this).closest('li.ui-module');
            $parentItems.remove();
            layer.msg('已删除！');
            $('.move-tisp-box').remove();
            _this.orderFn($(parentObj));
            _this.titleDelFn();
        });
    },
    //选择题添加选项
    topicACtrlFn: function (parentObj, addObj, batchAddObj, addAnswerObj) {
        //添加选项栏
        var $tid = 100 + parseInt(1000 * Math.random());
        $(document).on('click', parentObj + ' ' + addObj, function (e) {
            var tempId = $(this).attr('data-tempId');
            var notice=(tempId==="ui_additem_content")?'选项':'子题';
            var $parentItems = $(this).closest('li.ui-module').find('.cq-unset-list');
            var $name = $.trim($parentItems.attr('data-nameStr'));
            var index = $parentItems.children('li:last').index() + 2;
            $tid++;
            var data = {
                type: (tempId==="ui_additem_content")?$parentItems.attr('data-checktype'):'',
                name: $name,
                index: index,
                tid:index+parseInt(10000*Math.random()),
                items: [{id: $name.concat('_' + index), question:notice+index,tid:$tid+parseInt(10000*Math.random()),redirectNum:'',inputRule:''}]
            }
            $parentItems.append(template(tempId , data));
        });
        //选择题删除选项
        $(document).on('click', parentObj + ' ' + batchAddObj, function (e) {
            $(this).closest('li.ui-module').find('.cq-unset-list').children('li:last').remove();
        });
        //添加答案解析
        $(document).on('click', parentObj + ' ' + addAnswerObj, function (e) {
            $(this).closest('li').css({'height': 'auto'});
            var $parentItems = $(this).closest('li.ui-module').find('.cq-unset-list');
            if (!$(this).closest('li.ui-module').find('.analysis_contx').size()) {
                $parentItems.after(template('analysis_tmp', {}));
            } else {
                $(this).closest('li.ui-module').find('.analysis_contx').remove();
            }
        });
    }
}
