<?php

$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// get all products from products table
$result = mysql_query("SELECT * FROM pelanggan") or die(mysql_error());
 
// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["pelanggan"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $product = array();
        $product["id_pelanggan"] = $row["id_pelanggan"];
        $product["nama_pelanggan"] = $row["nama_pelanggan"];
        $product["no_hp"] = $row["no_hp"];
        $product["id_user"] = $row["id_user"];

        // push single product into final response array
        array_push($response["pelanggan"], $product);
    }
    // success
    //$response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No products found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>