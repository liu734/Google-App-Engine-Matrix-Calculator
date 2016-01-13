function check(){ 
username=document.form1.username.value; 
pwd1=document.form1.pwd1.value; 
pwd2=document.form1.pwd2.value; 
year=document.form1.year.value; 
month=document.form1.month.value; 
day=document.form1.day.value; 
email=document.form1.email.value; 
interest=document.form1.interest.value; 
if(username.length<6||username.length>15){ 
alert("Username is too short or too long！"); 
return false; 
} 
if(pwd1.length<6||pwd1.length>20){ 
alert("Password is too short or too long!"); 
return false; 
} 
if(pwd1!=pwd2){ 

alert("Password Failed!") 
return false; 
} 
if(year.length!=4 || month>13 || month<1 || day>32 || day<1){

alert("Error in the date of birth！");
return false; 
} 
if(email=""||(email.indexOf('@'==-1))||(email.indexOf('.')==-1)){ 

alert("Error in the email!"); 
return false; 
} 
return true; 
} 
