window.onload = () => {


    let canvas = document.getElementById("renderCanvas");
    let engine = new BABYLON.Engine(canvas, true);

    let createScene = function () {

        // Create the scene space
        let scene = new BABYLON.Scene(engine);

        //scene.clearColor = new BABYLON.Color3(0, 0, 0);

        // Parameters : name, position, scene
        let camera = new BABYLON.UniversalCamera("UniversalCamera", new BABYLON.Vector3(1000, 0, 0), scene);
        // Targets the camera to a particular position. In this case the scene origin
        camera.setTarget(BABYLON.Vector3.Zero());
        // Attach the camera to the canvas
        camera.attachControl(canvas, true);


        // light1
        let light = new BABYLON.PointLight("dir01", new BABYLON.Vector3(0, 0, 0), scene);
        light.position = new BABYLON.Vector3(0, 0, 0);

        let sunMaterial = new BABYLON.StandardMaterial("sunMaterial", scene);
        sunMaterial.diffuseTexture = new BABYLON.Texture("https://i.imgur.com/IkhvLCU.jpg", scene);

        let sun = BABYLON.Mesh.CreateSphere("sphere", 10, 50, scene);
        sun.position = light.position;
        sun.material = sunMaterial;
        sun.material.emissiveColor = new BABYLON.Color3(1, 1, 0);


        let mercur = BABYLON.Mesh.CreateSphere("sphere", 10, 5, scene);

        let mercurMaterial = new BABYLON.StandardMaterial("mercurMaterial", scene);
        mercurMaterial.diffuseTexture = new BABYLON.Texture("https://i.imgur.com/ZgU0gBA.png", scene);
        mercur.material = mercurMaterial;


        let venus = BABYLON.Mesh.CreateSphere("sphere", 10, 8, scene);
        let earth = BABYLON.Mesh.CreateSphere("sphere", 10, 8, scene);
        let mars = BABYLON.Mesh.CreateSphere("sphere", 10, 5, scene);
        let jupiter = BABYLON.Mesh.CreateSphere("sphere", 10, 20, scene);


        let moon = BABYLON.Mesh.CreateSphere("sphere", 10, 3, scene);

        let earthMaterial = new BABYLON.StandardMaterial("earthMaterial", scene);
        earthMaterial.diffuseTexture = new BABYLON.Texture("https://i.imgur.com/AdtLSqp.png", scene);
        earth.material = earthMaterial;

        let venusMaterial = new BABYLON.StandardMaterial("venusMaterial", scene);
        venusMaterial.diffuseTexture = new BABYLON.Texture("https://i.imgur.com/KC86Mxt.jpg", scene);
        venus.material = venusMaterial;

        let marsMaterial = new BABYLON.StandardMaterial("marsMaterial", scene);
        marsMaterial.diffuseTexture = new BABYLON.Texture("https://i.imgur.com/gNGTrJB.png", scene);
        mars.material = marsMaterial;

        let moonMaterial = new BABYLON.StandardMaterial("moonMaterial", scene);
        moonMaterial.diffuseTexture = new BABYLON.Texture("https://i.imgur.com/qs49y9O.jpg", scene);
        moon.material = moonMaterial;

        let jupiterMaterial = new BABYLON.StandardMaterial("jupiterMaterial", scene);
        jupiterMaterial.diffuseTexture = new BABYLON.Texture("https://i.imgur.com/cJiZREq.jpg", scene);
        jupiter.material = jupiterMaterial;

        let shadowGenerator = new BABYLON.ShadowGenerator(1024, light);
        shadowGenerator.getShadowMap().renderList.push(moon);
        shadowGenerator.useExponentialShadowMap = true;
        // shadowGenerator.useKernelBlur = true;
        // shadowGenerator.blurKernel = 64;

        earth.receiveShadows = true;

        // Animations
        let alphaSpeedMercur = 0;
        let alphaSpeedVenus = 0;
        let alphaSpeedEarth = 0;
        let alphaSpeedMoon = 0;
        let alphaSpeedMars = 0;
        let alphaSpeedJupiter = 0;
        let sunRotateTheta = Math.PI / 900;
        let mercurRotateTheta = Math.PI / 360;
        let i = 0;
        scene.beforeRender = function () {
            if (i >= 0) {
                mercur.position = new BABYLON.Vector3(70 * Math.sin(alphaSpeedMercur), 0, 60 * Math.cos(alphaSpeedMercur));
                venus.position = new BABYLON.Vector3(5, 120 * Math.sin(alphaSpeedVenus), 130 * Math.cos(alphaSpeedVenus));
                earth.position = new BABYLON.Vector3(200 * Math.sin(alphaSpeedEarth), -260 * Math.cos(alphaSpeedEarth), 0);
                moon.position = new BABYLON.Vector3(200 * Math.sin(alphaSpeedEarth) + 10 * Math.cos(alphaSpeedMoon), -260 * Math.cos(alphaSpeedEarth), -11 * Math.sin(alphaSpeedMoon));
                mars.position = new BABYLON.Vector3(350 * Math.sin(alphaSpeedMars), -300 * Math.cos(alphaSpeedMars), 0);
                jupiter.position = new BABYLON.Vector3(600 * Math.sin(alphaSpeedJupiter), 0, 500 * Math.cos(alphaSpeedJupiter));
                i++;

                alphaSpeedMercur += 0.02;
                alphaSpeedVenus += 0.008;
                alphaSpeedEarth += 0.005;
                alphaSpeedMars += 0.002;
                alphaSpeedJupiter += 0.0005;
            }

            moon.position = new BABYLON.Vector3(200 * Math.sin(alphaSpeedEarth) , -260 * Math.cos(alphaSpeedEarth) + 10 * Math.cos(alphaSpeedMoon), -11 * Math.sin(alphaSpeedMoon));

            sun.rotate(BABYLON.Axis.Y, sunRotateTheta, BABYLON.Space.LOCAL);
            mercur.rotate(BABYLON.Axis.Y, mercurRotateTheta, BABYLON.Space.LOCAL);
            venus.rotate(BABYLON.Axis.Y, Math.PI / 460, BABYLON.Space.LOCAL);
            earth.rotate(BABYLON.Axis.Y, Math.PI / 460, BABYLON.Space.LOCAL);
            moon.rotate(BABYLON.Axis.Y, Math.PI / 460, BABYLON.Space.LOCAL);
            mars.rotate(BABYLON.Axis.Y, Math.PI / 460, BABYLON.Space.LOCAL);
            jupiter.rotate(BABYLON.Axis.Y, Math.PI / 460, BABYLON.Space.LOCAL);


            alphaSpeedMoon += 0.05;

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
