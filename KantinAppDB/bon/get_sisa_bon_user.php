<?php

require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();

if (isset($_GET['id_pelanggan']) && isset($_GET['id_user'])) {

    $id_pelanggan = $_GET['id_pelanggan'];
    $id_user = $_GET['id_user'];


}

$result_total = mysql_query("SELECT SUM(harga_total) AS total FROM bon where id_pelanggan = '$id_pelanggan' AND id_user = '$id_user'");
$result_bayar = mysql_query("SELECT SUM(pembayaran) AS total FROM bon where id_pelanggan = '$id_pelanggan' AND id_user = '$id_user'");

if(mysql_num_rows($result_total) > 0){
    while($row = mysql_fetch_array($result_total)){
        echo $row['total'];
    }
    mysql_free_result($result_total);
} else{
    echo "No records matching your query were found.";
}

//$total = mysql_fetch_row($result_total);
//$bayar = mysql_fetch_row($result_bayar);

//echo $bayar[0];
//echo $total[0];


//$a = 100;
//$b = 200;

//echo ($b - $a);

?>