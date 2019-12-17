//验证帐号是否合法
function userValidity(value) {
    console.log(value);
    eval("var reg = /^[a-zA-Z]\w{3,15}");
    var re = new RegExp(reg);
    if (re.test(value)) {
        return true;
    }
    else {
        return false;
    }
}

//验证邮箱是否合法
function mailValidity(value) {
    eval("var email = /^(\\w-*\\.*)+@(\\w-?)+(\\.\\w{2,})+$/");
    var remail = new RegExp(email);
    if(remail.test(value)){
        return true;

    }else {
        return false;
    }
}

//验证手机号码是否合法
function phoneValidity(value) {
    eval("var phone =/^1[3-578]\\d{9}$/");
    var rephone = new RegExp(phone);
    if (rephone.test(value)){
        return true;
    } else {
        return false;
    }
}

function isValidBirthday(val) {
    var pattern = /^(19|20)\d{2}\-((0?[1-9])|(1[0-2]))\-((0?[1-9])|([1-2]\d)|3[01])$/;
    if(pattern.test(val)) {
        var date = new Date(val);
        if(date < new Date("1919-12-31") || date > new Date()) {
            return false;
        }
        var month = val.substring(val.indexOf("-")+1,val.lastIndexOf("-"));
        return date && (date.getMonth()+1 == parseInt(month));
    }
    return false;
}