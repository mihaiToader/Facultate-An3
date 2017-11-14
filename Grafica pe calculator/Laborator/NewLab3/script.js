window.onload = () => {


    let canvas = document.getElementById("renderCanvas");
    let engine = new BABYLON.Engine(canvas, true);

    let createScene = function () {

        // Create the scene space
        let scene = new BABYLON.Scene(engine);

        // scene.clearColor = new BABYLON.Color3(0, 0, 0);
        // Parameters : name, position, scene
        let camera = new BABYLON.UniversalCamera("UniversalCamera", new BABYLON.Vector3(10, 0, 0), scene);
        // Targets the camera to a particular position. In this case the scene origin
        camera.setTarget(BABYLON.Vector3.Zero());
        // Attach the camera to the canvas
        camera.attachControl(canvas, true);


        // light1
        let light = new BABYLON.PointLight("dir01", new BABYLON.Vector3(0, 0, 0), scene);
        light.position = new BABYLON.Vector3(0, 0, 0);

        let lightInBall1 = new BABYLON.PointLight("dir01", new BABYLON.Vector3(0, 0, 0), scene);
        lightInBall1.position = new BABYLON.Vector3(0, 3, 0);

        let box = BABYLON.MeshBuilder.CreateBox("box", {height: 1, width: 1, depth: 1}, scene);
        box.position = light.position;

        let boxMaterial = new BABYLON.StandardMaterial("boxMaterial", scene);
        boxMaterial.ambientTexture = new BABYLON.Texture("https://i.imgur.com/ooBfTk1.jpg", scene);
        box.material = boxMaterial;


        let centralBox = BABYLON.MeshBuilder.CreateBox("box", {height: 5, width: 1, depth: 2}, scene);
        centralBox.position = new BABYLON.Vector3(0,0,0);

        let centralBoxMaterial = new BABYLON.StandardMaterial("centralBoxMaterial", scene);
        centralBoxMaterial.ambientTexture = new BABYLON.Texture("https://i.imgur.com/mvRHdTv.jpg", scene);
        centralBox.material = centralBoxMaterial;

        let ball1 = BABYLON.Mesh.CreateSphere("sphere", 10, 3, scene);
        ball1.position = new BABYLON.Vector3(0, 3.5, 0);

        let ball1Material = new BABYLON.StandardMaterial("ball1Material", scene);
        ball1Material.ambientTexture = new BABYLON.Texture("https://i.imgur.com/Br5uHWf.jpg", scene);
        ball1.material = ball1Material;

        let ball2 = BABYLON.Mesh.CreateSphere("sphere", 10, 3, scene);
        ball2.position = new BABYLON.Vector3(0, -3.5, 0);
        ball2.material = ball1Material;


        // Animations
        let alphaSpeed = 0;

        scene.beforeRender = function () {
            light.position = new BABYLON.Vector3( 10 * Math.sin(alphaSpeed), 0, 20 * Math.cos(alphaSpeed));
            box.position = light.position;

            box.rotate(BABYLON.Axis.Y, Math.PI / 460, BABYLON.Space.LOCAL);
            centralBox.rotate(BABYLON.Axis.Y, Math.PI / 460, BABYLON.Space.LOCAL);

            alphaSpeed += 0.002;
        };

        return scene;

    };

    let scene = createScene();

    engine.runRenderLoop(function () {
        scene.render();
    });

    // Resize
    window.addEventListener("resize", function () {
        engine.resize();
    });
};
