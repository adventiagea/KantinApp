<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();

// array for JSON response
$response = array();
//print_r($_POST);
 
// check for required fields
if (isset($_POST['tanggal']) && isset($_POST['menu']) && isset($_POST['jumlah']) && isset($_POST['harga_satuan']) && isset($_POST['harga_total']) && isset($_POST['id_pelanggan']) && isset($_POST['id_user']) && isset($_POST['pembayaran'])) {
 
    $tanggal = $_POST['tanggal'];
    $menu = $_POST['menu'];
    $jumlah = $_POST['jumlah'];
    $harga_satuan = $_POST['harga_satuan'];
    $harga_total = $_POST['harga_total'];
    $id_pelanggan = $_POST['id_pelanggan'];
    $id_user = $_POST['id_user'];
    $pembayaran = $_POST['pembayaran'];

    // mysql inserting a new row
    $result = mysql_query("INSERT INTO bon(tanggal, menu, jumlah, harga_satuan, harga_total, id_pelanggan, id_user, pembayaran) VALUES('$tanggal', '$menu', '$jumlah', '$harga_satuan', '$harga_total', '$id_pelanggan', '$id_user','$pembayaran')");
 
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