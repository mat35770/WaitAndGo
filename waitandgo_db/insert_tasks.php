<?php
include_once './db_functions.php';
//Create Object for DB_Functions clas
$db = new DB_Functions(); 
//Get JSON posted by Android Application
$json = $_POST["tasksJSON"];
//Remove Slashes
if (get_magic_quotes_gpc()){
    $json = stripslashes($json);
}
//Decode JSON into an Array
$data = json_decode($json);
//Util arrays to create response JSON
$a=array();
$b=array();
//Loop through an Array and insert data read from JSON into MySQL DB
for($i=0; $i<count($data) ; $i++)
{
    //$db->createUserIfNotExist($data[$i]->name,$data[$i]->mail);
    //Store Task into MySQL DB
    $task_id = $db->storeTask($data[$i]->id,$data[$i]->title,$data[$i]->category,
        $data[$i]->description, $data[$i]->mail);
    
    //Based on inserttion, create JSON response
    if($task_id >= 1){
        $b["id"] = $data[$i]->id;
        $b["status"] = 'yes';
        $b["id_db_ext"] = $task_id;
        array_push($a,$b);
    }else{
        $b["id"] = $data[$i]->id;
        $b["status"] = 'no';
        array_push($a,$b);
    }
}
//Post JSON response back to Android Application
echo json_encode($a);
?>
