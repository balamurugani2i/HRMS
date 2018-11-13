<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/navigation.css' />" />
</head>
<body>
<div class="navbar" id="main">
  <a href="#" onclick="openNav()">&#9776;</a>
  <a href="/hrms/employee/displayEmployee">Employees</a>
  <a href="/hrms/project/displayAll">Projects</a>
  <a href="/hrms/client/displayClient">Clients</a>
  <a href="/hrms/designation/displayDesignation">Designation</a>
  <div style="float:right">
      <a href="/hrms/user/logout">logout</a>
  </div>
</div>

<div id="mySidenav" class="sidenav">
      <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
      <a href="/hrms/employee/viewProfile">My Profile</a>
      <a href="/hrms/employee/createProfile">Edit Profile</a>
      <a href="/hrms/employee/empProjects">Projects</a>
      <a href="/hrms/employee/empTasks">Time Sheet</a>
      <a href="/hrms/employee/empAttendance">Attendance</a>
</div>
</body>
<script>
function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
    document.getElementById("main").style.marginLeft = "250px";
}

function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
    document.getElementById("main").style.marginLeft= "0";
}
</script>
</html> 