<?php
include ('config.php');
 
class DB_Functions {
 
    private $con;

    // constructor
    function __construct() {
        // connecting to database
        $this->con=$this->connect_db(SERVEUR, UTILISATEUR, MDP);
    }
 
    // destructor
    function __destruct() {
 
    }
 
    // function to connect to the database
    function connect_db($serveur,$utilisateur,$mdp){  
        try{
            $pdo_options[PDO::ATTR_ERRMODE]=pdo::ERRMODE_EXCEPTION;
            $con=new PDO($serveur, $utilisateur, $mdp, $pdo_options);
            return $con;
        } catch (PDOException $e) {
            echo "<pre>";
            print_r($pdo_options);
            echo "</pre>";
            echo 'La connexion a échoué';
            echo $e->getMessage();
            return false;

        }
    }
    
    /**
     * Storing new task
     * returns task details
     */
    public function storeTask($id_local,$title,$category,$description,$mail) {
        $req=("INSERT INTO `task`(`id`, `title`, `category`, "
                . "`description`, `sync`, `id_local`) VALUES ('','$title','$category','$description','1','$id_local')");
        $result=  $this->con->query($req);        
           //si la requete présente une erreur on affiche le message d'erreur
        if ($result===FALSE){
            $errInfos=$this->con->errorInfo();
            echo 'requete échouée'.$errInfos[2];
            return 0; 
        } 
        else{
                $task_id = $this->con->lastInsertId();            
                $user_id = $this->getIdUser($mail);
                $res2 = $this->storeUserHasTask($user_id,$task_id);
                return $task_id;
        }
    }
    
    public function storeUserHasTask($users_id,$task_id){
        $req=("INSERT INTO `users_has_tasks`(`users_id`, `task_id`) "
                . "VALUES ($users_id,$task_id)");
        $result=  $this->con->query($req);
           //si la requete présente une erreur on affiche le message d'erreur
        if ($result===FALSE){
            $errInfos=$this->con->errorInfo();
            echo 'requete échouée'.$errInfos[2];
            return false; 
        }
    }
    
    public function getIdUser($mail){
        $req = ('SELECT id FROM users WHERE mail ="'.$mail.'"');
        $result= $this->con->query($req);
        $data = $result->fetch();
        $id = $data['id'];
        return $id;
    }
    
    public function createUserIfNotExist($name, $mail){
        $req = ('SELECT id FROM users WHERE mail ="'.$mail.'"');
        $result= $this->con->query($req);
        $number_user = $result->rowCount();
        if ($number_user === 0){
            $req = ('INSERT INTO users (`id`, `name`, `mail`) VALUES '
                    . "('','$name','$mail')");
            $result= $this->con->query($req);
        }
    }


    /**
     * Getting all tasks
     */
    public function getAllTasks() {
        $req = ("select * FROM task");
        $result= $this->con->query($req);
        return $result;
    }
    
    
    // Closing database connection
    public function close($con) {
        mysqli_close($con);
    }
    
    public function getUnSyncRowCount(){
        $req = ("SELECT * FROM task WHERE sync = 0");
        $result = $this->con->query($req);
        return $result;
    }
    
    public function updateSync($id, $status){
        $req = ("UPDATE task SET sync = $status WHERE id = $id");
        $result = $this->con->query($req);
        return $result;
    }
    
}
 
?>
