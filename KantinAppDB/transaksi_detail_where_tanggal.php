<?php
 
/*
 * Following code will get single transaksi details
 * A transaksi is identified by transaksi id (id_pelanggan)
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// check for post data
if (isset($_GET["tanggal"])) {
    $tanggal = $_GET['tanggal'];
 
    // get a transaksi from transaksis table
    $result = mysql_query("SELECT * FROM transaksi WHERE tanggal = $tanggal");
 
    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
 
            $result = mysql_fetch_array($result);
 
            $transaksi = array();
            $transaksi["id_pelanggan"] = $result["id_pelanggan"];
            $transaksi["id_transaksi"] = $result["id_transaksi"];
            $transaksi["tanggal"] = $result["tanggal"];
            $transaksi["id_bon"] = $result["id_bon"];
            // success
            $response["success"] = 1;
 
            // user node
            $response["transaksi"] = array();
 
            array_push($response["transaksi"], $transaksi);
 
            // echoing JSON response
            echo json_encode($response);
        } else {
            // no transaksi found
            $response["success"] = 0;
            $response["message"] = "No transaksi found";
 
            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no transaksi found
        $response["success"] = 0;
        $response["message"] = "No transaksi found";
 
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