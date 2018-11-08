<html>
<head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
.navbar {
    overflow: hidden;
    background-color: #333;
    font-family: Arial, Helvetica, sans-serif;
}

.navbar a {
    float: left;
    font-size: 16px;
    color: white;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
}

.dropdown {
    float: left;
    overflow: hidden;
}

.dropdown .dropbtn {
    font-size: 16px;    
    border: none;
    outline: none;
    color: white;
    padding: 14px 16px;
    background-color: inherit;
    font-family: inherit;
    margin: 0;
}

.navbar a:hover, .dropdown:hover .dropbtn {
    background-color: red;
}

.dropdown-content {
    display: none;
    position: absolute;
    background-color: #f9f9f9;
    min-width: 160px;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    z-index: 1;
}

.dropdown-content a {
    float: none;
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
    text-align: left;
}

.dropdown-content a:hover {
    background-color: #ddd;
}

.dropdown:hover .dropdown-content {
    display: block;
}
</style>
</head>
<body>

<div class="navbar">
  <div class="dropdown">
    <button class="dropbtn">&#9776;</button>
    <div class="dropdown-content">
      <a href="/HRMS/employee/viewProfile">Edit Profile</a>
      <a href="/HRMS/employee/empProjects">Projects</a>
      <a href="#" onclick="viewTask()">Time Sheet</a>
      <a href="/HRMS/user/logout">Attendance</a>
    </div>
  </div> 
  <div style="float:center">
      <a href="#" >Welcome ${employee.name}</a>
  <div>
  <div style="float:right">
      <a href="#" onclick="viewAttendance()">LogOut</a>
  <div>
</div>

<script>
function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
}

function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
}

function viewTask() {
    document.getElementById("taskInfo").style.display='block';
    document.getElementById("projectInfo").style.display='none';
    document.getElementById("attendanceInfo").style.display='none';
}

function viewAttendance() {
    document.getElementById("taskInfo").style.display='none';
    document.getElementById("projectInfo").style.display='none';
    document.getElementById("attendanceInfo").style.display='block';
}
</script>
     
</body>
</html> 