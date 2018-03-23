/**
 * Title:
 * @author Mr Denzel
 * @create Date 2017-11-10 14:40
 * @version 1.0
 * Description:
 */
$(function () {
    var examInfo =JSON.parse(sessionStorage.getItem('exam'))||{};
    examInfo&&(examInfo.hasOwnProperty('exam_id'))&&initContent(examInfo);
    $('.add_button').on('click',function () {
        $('.add_shadow').css({display:'block'});
    })
    $('.button-event').on('click','a',function () {
        if(this.id==='info_sure'){
            var exam_title = $('#exam_name').val();
            if(exam_title.length<1){
                alert('试题名称不能为空');
                return ;
            }
            examInfo&&(!examInfo.hasOwnProperty('exam_id'))&&(examInfo.exam_id = (Math.random()*100000).toFixed(0)+5163); //首次需要动态的为考题创建一个ID
            examInfo.exam_name = exam_title ;
            examInfo.exam_about = $('#exam_info').val();
            $('.exam-page .content .title').text(exam_title);
            ($('.exam-page').css('display')==='none')&&($('.exam-page').addClass('show'));
            console.log(examInfo);
            $('.add_shadow').css({display:'none'});
            sessionStorage.setItem('exam',JSON.stringify(examInfo));
        }else{
            $('.add_shadow').addClass('hidden');
        }
    })
    function initContent(data) {
        $('#exam_name').val(data.exam_name);
        $('#exam_info').val(data.exam_about);
        $('.exam-page .content .title').text(data.exam_name);
        $('.exam-page .content .about').text(data.exam_about);
        ($('.exam-page').css('display')==='none')&&($('.exam-page').addClass('show'));
    }
})