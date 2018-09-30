<?php
    header('Content-Type: application/json; charset=utf-8');
    $conn = mysqli_connect("localhost", "", "", "");
    $conn->set_charset("utf8mb4");
    $id = mysqli_real_escape_string($conn, $_GET["id"]);
    
    $query = "SELECT description, devices_json, phone, location_long, time_long, tags, star, review_cnt, latitude, longitude FROM arcade_list WHERE id='$id'";
    $res = mysqli_query($conn, $query);
    
    if ($res && ($row = mysqli_fetch_assoc($res))) {
        $row['star'] = (double)$row['star'];
        $row['review_cnt'] = (int)$row['review_cnt'];
        $row['latitude'] = (double)$row['latitude'];
        $row['longitude'] = (double)$row['longitude'];
        $row['tags'] = str_replace(',', ', ', trim($row['tags'], ','));
        $row['comments'] = array();
        $row['devices'] = json_decode(file_get_contents($row['devices_json']));
        unset($row['devices_json']);
    } else {
        mysqli_close($conn);
        http_response_code(503);
        die("error");
    }

    $query = "SELECT author, content, star, written_date FROM arcade_reviews WHERE place_id='$id' ORDER BY id DESC";
    $res = mysqli_query($conn, $query);

    if ($res) {
        while ($temp = mysqli_fetch_assoc($res)) {
            $temp['star'] = (double)$temp['star'];
            $row['comments'][] = $temp;
        }
    } else {
        mysqli_close($conn);
        http_response_code(503);
        die("error");
    }

    mysqli_close($conn);
    $json = json_encode($row, JSON_UNESCAPED_UNICODE);
    echo $json;
?>