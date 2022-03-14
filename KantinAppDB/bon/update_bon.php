<?php
 
/*
 * Following code will update a product information
 * A product is identified by product id (pid)
 */
 
// array for JSON response
$response = array();

require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
$db = new DB_CONNECT();
 
// check for required fields
if (isset($_POST['id_bon']) && isset($_POST['tanggal']) && isset($_POST['menu']) && isset($_POST['jumlah']) && isset($_POST['harga_satuan']) && isset($_POST['harga_total']) && isset($_POST['id_pelanggan']) && isset($_POST['id_user']) && isset($_POST['pembayaran'])) {
 
    $id_bon = $_POST['id_bon'];
    $tanggal = $_POST['tanggal'];
    $menu = $_POST['menu'];
    $jumlah = $_POST['jumlah'];
    $harga_satuan = $_POST['harga_satuan'];
    $harga_total = $_POST['harga_total'];
    $id_pelanggan = $_POST['id_pelanggan'];
    $id_user = $_POST['id_user'];
    $pembayaran = $_POST['pembayaran'];

 
    // include db connect class
    
 
    // mysql update row with matched pid
    $result = mysql_query("UPDATE bon SET tanggal = '$tanggal', menu = '$menu', jumlah = '$jumlah', harga_satuan = '$harga_satuan', harga_total = '$harga_total', pembayaran = '$pembayaran', id_user = '$id_user', id_pelanggan = '$id_pelanggan' where id_bon = '$id_bon'");
 
    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Item successfully updated.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
 
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>