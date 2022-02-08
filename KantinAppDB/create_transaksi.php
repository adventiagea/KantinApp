<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['id_transaksi']) && isset($_POST['tanggal']) && isset($_POST['id_bon']) && isset($_POST['id_pelanggan'])) {
 
    $id_transaksi = $_POST['id_transaksi'];
    $tanggal = $_POST['tanggal'];
    $id_bon = $_POST['id_bon'];
    $id_pelanggan = $_POST['id_pelanggan']
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql inserting a new row
    $result = mysql_query("INSERT INTO transaksi(id_transaksi, tanggal, id_bon, id_pelanggan) VALUES('$id_transaksi', '$tanggal', '$id_bon', '$id_pelanggan')");
 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Product successfully created.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
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