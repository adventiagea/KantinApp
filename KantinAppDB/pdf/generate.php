<?php

require_once __DIR__ . '/vendor/autoload.php';
require_once __DIR__ . '/db_connect.php';
require_once __DIR__ . '/random_compat/lib/random.php';
 
// connecting to db
$db = new DB_CONNECT();

$mpdf=new \Mpdf\Mpdf();

if (isset($_GET['id_pelanggan']) && isset($_GET['id_user'])) {

    $id_pelanggan = $_GET['id_pelanggan'];
    $id_user = $_GET['id_user'];


}

$result_kantin = mysql_query("SELECT * FROM user WHERE id_user = '$id_user'");
$result_pelanggan = mysql_query("SELECT * FROM pelanggan WHERE id_pelanggan = '$id_pelanggan'");
$result = mysql_query("SELECT * FROM bon where id_pelanggan = '$id_pelanggan' AND id_user = '$id_user'");
$result_total = mysql_query("SELECT SUM(harga_total) AS total FROM bon where id_pelanggan = '$id_pelanggan' AND id_user = '$id_user'");
$result_bayar = mysql_query("SELECT SUM(pembayaran) AS total FROM bon where id_pelanggan = '$id_pelanggan' AND id_user = '$id_user'");


if(mysql_num_rows($result_kantin) > 0){

	$html_kantin='<style></style><table class="user">';
		
		while($row=mysql_fetch_assoc($result_kantin)){
			$html_kantin.='<tr><td>Kantin :</td><td>'.$row['username'].'</td></tr>';
		}

	$html_kantin.='</table>';
}
else{
	$html_kantin="Data not found";
}

if(mysql_num_rows($result_pelanggan) > 0){

	$html_pelanggan='<style></style><table class="pelanggan">';
		
		while($row=mysql_fetch_assoc($result_pelanggan)){
			$html_pelanggan.='<tr><td>Pelanggan :</td><td>'.$row['nama_pelanggan'].'</td></tr>';
		}

	$html_pelanggan.='</table><br>';
}
else{
	$html_pelanggan="Data not found";
}

if(mysql_num_rows($result) > 0){

	$html='<style></style><table class="table">';
		
		$html.='<tr><td>Tanggal</td><td>Menu</td><td>Jumlah</td><td>Harga</td><td>Total</td><td>Pembayaran</td></tr>';
		
		while($row=mysql_fetch_assoc($result)){
			$html.='<tr><td>'.$row['tanggal'].'</td><td>'.$row['menu'].'</td><td>'.$row['jumlah'].'</td><td>'.$row['harga_satuan'].'</td><td>'.$row['harga_total'].'</td><td>'.$row['pembayaran'].'</td></tr>';
		}

	$html.='</table>';
}
else{
	$html="Data not found";
}

if(mysql_num_rows($result_total) > 0){

	$html_total='<style></style><table class="total">';
		
		while($row=mysql_fetch_assoc($result_total)){
			$html_total.='<tr><td><br><b>Total Bon :</b></td><td><br><b>'.$row['total'].'</b></td></tr>';
		}

	$html_total.='</table>';
}
else{
	$html_total="Data not found";
}

if(mysql_num_rows($result_bayar) > 0){

	$html_bayar='<style></style><table class="total">';
		
		while($row=mysql_fetch_assoc($result_bayar)){
			$html_bayar.='<tr><td><b>Total Pembayaran :</b></td><td><b>'.$row['total'].'</b></td></tr>';
		}

	$html_bayar.='</table>';
}
else{
	$html_bayar="Data not found";
}

$mpdf->WriteHTML($html_kantin);
$mpdf->WriteHTML($html_pelanggan);
$mpdf->WriteHTML($html);
$mpdf->WriteHTML($html_total);
$mpdf->WriteHTML($html_bayar);
//$file='media/'.time().'.pdf';
$mpdf->output();

?>