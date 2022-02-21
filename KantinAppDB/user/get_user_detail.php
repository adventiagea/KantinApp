<?php
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// check for post data
if (isset($_GET['username'])) {
    $username = $_GET['username'];
 
    // get a product from products table
    $result = mysql_query("SELECT * FROM user WHERE username = $username");

    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
 
            $result = mysql_fetch_array($result);
 
            $product = array();
            $product["id_user"] = $result["id_user"];
            $product["password"] = $result["password"];
            $product["username"] = $result["username"];
            $product["nama_kantin"] = $result["nama_kantin"];
            
            // success
 
            // user node
            //$response["user"] = array();
 
            //array_push($response, $product);
 
            // echoing JSON response
            echo json_encode($product);
        } else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "No product found";
 
            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No product found";
 
        // echo no users JSON
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>