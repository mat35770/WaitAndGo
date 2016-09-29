<html>
<head><title>View Users</title>
<style>
body {
  font: normal medium/1.4 sans-serif;
}
table {
  border-collapse: collapse;
  width: 20%;
   margin-left: auto;
    margin-right: auto;
}
tr > td {
  padding: 0.25rem;
  text-align: center;
  border: 1px solid #ccc;
}
tr:nth-child(even) {
  background: #FAE1EE;
}
tr:nth-child(odd) {
  background: #edd3ff;
}
tr#header{
background: #c1e2ff;
}
div.header{
padding: 10px;
background: #e0ffc1;
width:30%;
color: #008000;
margin:5px;
}
div.refresh{
margin-top:10px;
width: 5%;
margin-left: auto;
margin-right: auto;
}
div#norecord{
margin-top:10px;
width: 15%;
margin-left: auto;
margin-right: auto;
}
</style>
<script>
function refreshPage(){
location.reload();
}
</script>
</head>
<body>
<center>
<div class="header">
Android SQLite and MySQL Sync Results
</div>
</center>
<?php
    include_once 'db_functions.php';
    $db = new DB_Functions();
    $result = $db->getAllTasks();
     if ($result===FALSE){
        echo 'requete échouée'.$errInfos[2];
        return false; 
    }
    else{
        $number_tasks = $result->rowCount();
    }
    if ($number_tasks > 0) {
?>
<table>
<tr id="header"><td>Id</td><td>Title</td></tr>
<?php
    while ($row = mysqli_fetch_array($result)) { 
        if (!$row) {
    printf("Error: %s\n", mysqli_error($con));
        exit();}
?> 
<tr>
<td><span><?php echo $row["id"] ?></span></td>
<td><span><?php echo $row["title"] ?></span></td>
</tr>
<?php } ?>
</table>
<?php }else{ ?>
<div id="norecord">
No records in MySQL DB
</div>
<?php } ?>
<div class="refresh">
<button onclick="refreshPage()">Refresh</button>
</div>
</body>
</html>
