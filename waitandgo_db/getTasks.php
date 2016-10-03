<?php
/**
 * Creates Unsynced rows data as JSON
 */
    include_once 'db_functions.php';
    $db = new DB_Functions();
    $json = $_POST["mail"];
    //Remove Slashes
    if (get_magic_quotes_gpc()){
        $json = stripslashes($json);
    }
    //Decode JSON into an Array
    $data = json_decode($json);
    $id_user = $db->getIdUser($data[0]->mail);
    $tasks = $db->getUnSyncRowCount();
    $a = array();
    $b = array();
    
    if($tasks === FALSE) { 
        echo mysql_error();
    }
    else{
        while ($row = mysqli_fetch_array($tasks)) {      
        $b["taskId"] = $row["id"];
        $b["title"] = $row["title"];
        $b["category"] = $row["category"];
        $b["description"] = $row["description"];
        array_push($a,$b);
        }
        echo json_encode($a);
    }

    
    
?>