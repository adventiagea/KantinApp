<?php
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// check for post data
if (isset($_GET['nama_pelanggan']) && isset($_GET['id_user'])) {
    $nama_pelanggan = $_GET['nama_pelanggan'];
    $id_user = $_GET['id_user'];
 
    // get a product from products table
    $result = mysql_query("SELECT * FROM pelanggan WHERE nama_pelanggan = $nama_pelanggan AND id_user = $id_user");
 
    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
 
            $result = mysql_fetch_array($result);
 
            $product = array();
            $product["id_pelanggan"] = $result["id_pelanggan"];
            $product["nama_pelanggan"] = $result["nama_pelanggan"];
            $product["no_hp"] = $result["no_hp"];
            $product["id_user"] = $result["id_user"];
            
            // success
            //$response["success"] = 1;
 
            // user node
            $response["pelanggan"] = array();
 
            array_push($response["pelanggan"], $product);
 
            // echoing JSON response
            echo json_encode($response);
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