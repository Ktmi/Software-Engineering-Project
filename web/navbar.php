<header>
    <nav>
        <div class = "navlinks">
            <div><img></img></div>
            <div><a href="/">Home</a></div>
            <?php 
            if (isset($_COOKIE["user"])) { ?>
            <div><a href="profile">Profile</a></div>
            <div><a href="profile">Profile</a></div>
            <?php
            } else { ?>
                <div><a href="/login/">Log-in</a></div>
            <?php
            }
            ?>
            <div></div>    
        </div>
    </nav>
    <script>
        
    </script>
</header>