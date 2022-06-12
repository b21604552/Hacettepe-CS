"use strict";

var canvas;
var gl;

var theta = 0.0;
var thetaLoc;
var speed = 0.1;
var animation = true;
var colorData = [];
var program;
var colorBuffer;
var colorLocation;
window.onload = function init()
{
    canvas = document.getElementById( "gl-canvas" );

    gl = WebGLUtils.setupWebGL( canvas );
    if ( !gl ) { alert( "WebGL isn't available" ); }

    //
    //  Configure WebGL
    //
    gl.viewport( 0, 0, canvas.width, canvas.height );
    gl.clearColor( 1.0, 1.0, 1.0, 1.0 );

    //  Load shaders and initialize attribute buffers
    program = initShaders( gl, "vertex-shader", "fragment-shader" );
    gl.useProgram( program );

    var vertices = [
        vec2(  0,  0.577 ),
        vec2(  -0.5,  -0.2886 ),
        vec2(  0.5,  -0.2886 ),
    ];

    randomColor();

    // Load the data into the GPU
    var bufferId = gl.createBuffer();
    gl.bindBuffer( gl.ARRAY_BUFFER, bufferId );
    gl.bufferData( gl.ARRAY_BUFFER, flatten(vertices), gl.STATIC_DRAW );

    // Associate out shader variables with our data buffer
    var vPosition = gl.getAttribLocation( program, "vPosition" );
    gl.enableVertexAttribArray( vPosition );
    gl.bindBuffer( gl.ARRAY_BUFFER, bufferId );
    gl.vertexAttribPointer( vPosition, 2, gl.FLOAT, false, 0, 0 );

    colorChange()

    thetaLoc = gl.getUniformLocation( program, "theta" );

    render();
};

function toggleAnimation() {
    animation = !animation
}

function speedUp() {
    speed += 0.02
}

function slowDown() {
    if(speed > 0)
        speed -= 0.02
}

function randomColor() {
    colorData = []
    for (var i = 0; i < 9; i++){
        colorData.push(Math.random())
    }
}

function colorChange() {
    colorBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, colorBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, flatten(colorData), gl.STATIC_DRAW);
    colorLocation = gl.getAttribLocation(program, `color`);
    gl.enableVertexAttribArray(colorLocation);
    gl.bindBuffer(gl.ARRAY_BUFFER, colorBuffer);
}

function render() {

    gl.clear( gl.COLOR_BUFFER_BIT );

    if(animation)
        theta += speed;
    else
        theta -= speed;

    colorChange();
    
    gl.vertexAttribPointer(colorLocation, 3, gl.FLOAT, false, 0, 0);
    gl.uniform1f( thetaLoc, theta );
    gl.drawArrays( gl.LINE_LOOP, 0, 3 );

    window.requestAnimFrame(render);
}
