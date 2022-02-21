<?php
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// check for post data
if (isset($_GET["id_bon"])) {
    $pid = $_GET['id_bon'];
 
    // get a product from products table
    $result = mysql_query("SELECT *FROM bon WHERE id_bon = $id_bon");
 
    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
 
            $result = mysql_fetch_array($result);
 
            $product = array();
            $product["id_bon"] = $result["id_bon"];
            $product["id_menu"] = $result["id_menu"];
            $product["jumlah"] = $result["jumlah"];
            $product["id_pelanggan"] = $result["id-pelanggan"];
       

            // success
            $response["success"] = 1;
 
            // user node
            $response["bon"] = array();
 
            array_push($response["bon"], $product);
 
            // echoing JSON response
            echo json_encode($response);
        } else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "No item found";
 
            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No item found";
 
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