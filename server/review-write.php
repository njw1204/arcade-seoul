<?php
    header('Content-Type: application/json; charset=utf-8');
    $conn = mysqli_connect("localhost", "", "", "");
    $conn->set_charset("utf8mb4");
    $place_id = mysqli_real_escape_string($conn, $_POST["place_id"]);
    $author = mysqli_real_escape_string($conn, $_POST["author"]);
    $content = mysqli_real_escape_string($conn, $_POST["content"]);
    $star = mysqli_real_escape_string($conn, $_POST["star"]);
    $written_date = date("Y-m-d");

    $query = "SELECT COUNT(*) FROM arcade_list WHERE id='$place_id'";
    $res = mysqli_query($conn, $query);
    if (!res || mysqli_fetch_array($res)[0] != '1') {
        mysqli_close($conn);
        http_response_code(503);
        die("error");
    }

    $query = "INSERT INTO arcade_reviews (place_id,author,content,star,written_date) VALUES ('$place_id','$author','$content','$star','$written_date')";
    $res = mysqli_query($conn, $query);
    
    if (!res) {
        mysqli_close($conn);
        http_response_code(503);
        die("error");
    }

    $query = "SELECT AVG(star), COUNT(*) FROM arcade_reviews WHERE place_id='$place_id'";
    $result = mysqli_fetch_array(mysqli_query($conn, $query));
    $star = round((float)$result[0], 1);
    $review_cnt = (int)$result[1];

    $query = "UPDATE arcade_list SET star='$star', review_cnt='$review_cnt' WHERE id='$place_id'";
    mysqli_query($conn, $query);
    mysqli_close($conn);
    echo "ok";
?>