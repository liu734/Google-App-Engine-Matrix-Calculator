function popup(page, w, h) {
  var config = "toolbar=no,menubar=no,location=no,directories=no," +  
               "status=no,resizable=yes,scrollbars=yes,width=" + w + 
               ",height="+ h + ",top=50,left=45";
  return window.open(page, "", config);
}