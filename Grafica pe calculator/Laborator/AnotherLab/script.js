window.onload = () => {


    let canvas = document.getElementById("renderCanvas");
    let engine = new BABYLON.Engine(canvas, true);

    let createScene = function () {

        let scene = new BABYLON.Scene(engine);

        // Light
        let spot = new BABYLON.PointLight("spot", new BABYLON.Vector3(0, 30, 10), scene);
        spot.diffuse = new BABYLON.Color3(1, 1, 1);
        spot.specular = new BABYLON.Color3(0, 0, 0);

        // Camera
        let camera = new BABYLON.ArcRotateCamera("Camera", 0, 0.8, 100, BABYLON.Vector3.Zero(), scene);
        camera.lowerBetaLimit = 0.1;
        camera.upperBetaLimit = (Math.PI / 2) * 0.9;
        camera.lowerRadiusLimit = 30;
        camera.upperRadiusLimit = 150;
        camera.attachControl(canvas, true);

        let plane = BABYLON.Mesh.CreatePlane("plane", 120, scene);
        plane.position.y = -5;
        plane.rotation.x = Math.PI / 2;

        //Creation of a repeated textured material
        let materialPlane = new BABYLON.StandardMaterial("texturePlane", scene);
        materialPlane.diffuseTexture = new BABYLON.Texture("https://i.imgur.com/mvRHdTv.jpg", scene);
        materialPlane.diffuseTexture.uScale = 5.0;//Repeat 5 times on the Vertical Axes
        materialPlane.diffuseTexture.vScale = 5.0;//Repeat 5 times on the Horizontal Axes
        materialPlane.backFaceCulling = false;//Always show the front and the back of an element

        plane.material = materialPlane;

        //Sphere to see the light's position
        let sun = BABYLON.Mesh.CreateSphere("sun", 10, 4, scene);
        sun.material = new BABYLON.StandardMaterial("sun", scene);
        sun.material.emissiveColor = new BABYLON.Color3(1, 1, 0);

        // Skybox
        let skybox = BABYLON.Mesh.CreateBox("skyBox", 800.0, scene);
        let skyboxMaterial = new BABYLON.StandardMaterial("skyBox", scene);
        skyboxMaterial.backFaceCulling = false;
        skyboxMaterial.reflectionTexture = new BABYLON.CubeTexture("https://i.imgur.com/UjZWWew.jpg", scene);
        skyboxMaterial.reflectionTexture.coordinatesMode = BABYLON.Texture.SKYBOX_MODE;
        skyboxMaterial.diffuseColor = new BABYLON.Color3(0, 0, 0);
        skyboxMaterial.specularColor = new BABYLON.Color3(0, 0, 0);
        skyboxMaterial.disableLighting = true;
        skybox.material = skyboxMaterial;


        let myWall = BABYLON.MeshBuilder.CreateBox("myBox", {height: 10, width: 1, depth: 100}, scene);
        let myWallMaterial = new BABYLON.StandardMaterial("myWallMaterial", scene);
        myWallMaterial.diffuseTexture = new BABYLON.Texture("https://i.imgur.com/phq6mVM.jpg", scene);
        myWall.material = myWallMaterial;

        //Sun animation
        scene.registerBeforeRender(function () {
            sun.position = spot.position;
            spot.position.x -= 0.5;
            if (spot.position.x < -90)
                spot.position.x = 100;
        });

        let shadowGenerator = new BABYLON.ShadowGenerator(1024, spot);
        shadowGenerator.getShadowMap().renderList.push(myWall);
        shadowGenerator.useExponentialShadowMap = true;


        plane.receiveShadows = true;


        scene.enablePhysics(null, new BABYLON.OimoJSPlugin());

        let materialAmiga = new BABYLON.StandardMaterial("amiga", scene);
        materialAmiga.diffuseTexture = new BABYLON.Texture("https://i.imgur.com/AdtLSqp.png", scene);
        materialAmiga.emissiveColor = new BABYLON.Color3(0.5, 0.5, 0.5);

        // Spheres
        let y = 0;
        for (let index = 0; index < 10; index++) {
            let sphere = BABYLON.Mesh.CreateSphere("Sphere0", 16, 3, scene);
            sphere.material = materialAmiga;
            sphere.position = new BABYLON.Vector3(Math.random() * 20 - 10, y, Math.random() * 10 - 5);

            shadowGenerator.getShadowMap().renderList.push(sphere);

            sphere.physicsImpostor = new BABYLON.PhysicsImpostor(sphere, BABYLON.PhysicsImpostor.SphereImpostor, { mass: 1 }, scene);

            y += 2;
        }

        // Link
        let spheres = [];
        for (index = 0; index < 10; index++) {
            sphere = BABYLON.Mesh.CreateSphere("Sphere0", 16, 1, scene);
            spheres.push(sphere);
            sphere.position = new BABYLON.Vector3(Math.random() * 20 - 10, y, Math.random() * 10 - 5);
            sphere.material = materialAmiga;

            shadowGenerator.getShadowMap().renderList.push(sphere);

            sphere.physicsImpostor = new BABYLON.PhysicsImpostor(sphere, BABYLON.PhysicsImpostor.SphereImpostor, { mass: 1 }, scene);
        }

        for (index = 0; index < 9; index++) {
            spheres[index].setPhysicsLinkWith(spheres[index + 1], new BABYLON.Vector3(0, 0.5, 0), new BABYLON.Vector3(0, -0.5, 0));
        }

        // Box
        let box0 = BABYLON.Mesh.CreateBox("Box0", 3, scene);
        box0.position = new BABYLON.Vector3(3, 30, 0);
        let materialWood = new BABYLON.StandardMaterial("wood", scene);
        materialWood.diffuseTexture = new BABYLON.Texture("https://i.imgur.com/g4CLo0V.jpg", scene);
        materialWood.emissiveColor = new BABYLON.Color3(0.5, 0.5, 0.5);
        box0.material = materialWood;

        shadowGenerator.getShadowMap().renderList.push(box0);

        // Compound
        let part0 = BABYLON.Mesh.CreateBox("part0", 3, scene);
        part0.position = new BABYLON.Vector3(3, 30, 0);
        part0.material = materialWood;

        let part1 = BABYLON.Mesh.CreateBox("part1", 3, scene);
        part1.parent = part0; // We need a hierarchy for compound objects
        part1.position = new BABYLON.Vector3(0, 3, 0);
        part1.material = materialWood;

        shadowGenerator.getShadowMap().renderList.push(part0);
        shadowGenerator.getShadowMap().renderList.push(part1);
        shadowGenerator.useBlurExponentialShadowMap = true;
        shadowGenerator.useKernelBlur = true;
        shadowGenerator.blurKernel = 32;





        // Physics
        box0.physicsImpostor = new BABYLON.PhysicsImpostor(box0, BABYLON.PhysicsImpostor.BoxImpostor, { mass: 2, friction: 0.4, restitution: 0.3 }, scene);
        myWall.physicsImpostor = new BABYLON.PhysicsImpostor(myWall, BABYLON.PhysicsImpostor.BoxImpostor, { mass: 0, friction: 0.5, restitution: 0.7 }, scene);
        plane.physicsImpostor = new BABYLON.PhysicsImpostor(plane, BABYLON.PhysicsImpostor.BoxImpostor, { mass: 0, friction: 0.5, restitution: 0.7 }, scene);

        part0.physicsImpostor = new BABYLON.PhysicsImpostor(part0, BABYLON.PhysicsImpostor.BoxImpostor, { mass: 2, friction: 0.4, restitution: 0.3 }, scene);

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
