<header>
    <nav>
        <div class = "navlinks">
            <div><img src="https://dejpknyizje2n.cloudfront.net/svgcustom/clipart/preview/serious-blue-jay-head-sticker-31770-300x300.png" 
            width="55%" height="55%" alt="#"></img></div>
            <div><a href="/"><h3>  &nbspHome&nbsp  </h3></a></div>
            <div><a href="/"><h3>  &nbspBrowse&nbsp  </h3></a></div>
            <div><a href="/"><h3>  &nbspProfile&nbsp  </h3></a></div>
            <?php 
            if (isset($_COOKIE["user"])) { ?>
            <div><a href="/profile"><h3>  &nbspProfile&nbsp  </h3></a></div>
            <div><a href="/logout"><h3>  &nbspLogout&nbsp  </h3></a></div>
            <?php
            } else { ?>
                <div><a href="/login/"><h3>  &nbspLogin&nbsp  </h3></a></div>
            <?php
            }
            ?>
            <div></div>    
        </div>
    </nav>
    <script>
        
    </script>
</header>