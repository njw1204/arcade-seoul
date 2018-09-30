<?php
    header('Content-Type: application/json; charset=utf-8');
    $conn = mysqli_connect("localhost", "", "", "");
    $conn->set_charset("utf8mb4");
    $type = mysqli_real_escape_string($conn, $_GET["type"]);

    if ($type == "all") {
        $query = "SELECT id, name, location_short, time_short, tags, star, review_cnt FROM arcade_list";
    } else if ($type == "area") {
        $area = mysqli_real_escape_string($conn, $_GET["area"]);
        $query = "SELECT id, name, location_short, time_short, tags, star, review_cnt FROM arcade_list WHERE location_short='$area'";
    } else if ($type == "theme") {
        $theme = mysqli_real_escape_string($conn, $_GET["theme"]);
        $query = "SELECT id, name, location_short, time_short, tags, star, review_cnt FROM arcade_list WHERE tags LIKE '%,$theme,%'";
    } else if ($type == "fav") {
        $fav = mysqli_real_escape_string($conn, $_GET["fav"]);
        $query = "SELECT id, name, location_short, time_short, tags, star, review_cnt FROM arcade_list WHERE id IN ($fav)";
    } else {
        mysqli_close($conn);
        http_response_code(400);
        die("error");
    }

    $res = mysqli_query($conn, $query);
    $result = array();
    if ($res) {
        while ($row = mysqli_fetch_assoc($res)) {
            $row['id'] = (int)$row['id'];
            $row['star'] = (double)$row['star'];
            $row['review_cnt'] = (int)$row['review_cnt'];
            $result[] = $row;
        }
    } else {
        mysqli_close($conn);
        http_response_code(503);
        die("error");
    }

    mysqli_close($conn);
    $json = json_encode($result, JSON_UNESCAPED_UNICODE);
    echo $json;
?>