function draw(canvasID) {
    const canvas = document.getElementById(canvasID);
    gl = canvas.getContext("webgl2");

    if(!gl){
        console.error("WebGL context is not available.");
        return null;
    }
    
    ShaderUtil.clear();

    let vShaderTxt	= ShaderUtil.domShaderSrc("vertex_shader"),
        fShaderTxt	= ShaderUtil.domShaderSrc("fragment_shader"),
        vertexShader = ShaderUtil.createShader(vShaderTxt, gl.VERTEX_SHADER),
        fragmentShader = ShaderUtil.createShader(fShaderTxt, gl.FRAGMENT_SHADER);

    const program = ShaderUtil.createProgram(vertexShader,fragmentShader);
    colorLocation = gl.getAttribLocation(program, "a_color");
    vertexLocation = gl.getAttribLocation(program, "a_position");

    gl.useProgram(program);

    ShaderUtil.drawFace()
    ShaderUtil.drawEyes();
    ShaderUtil.drawMask();
    ShaderUtil.drawCurves();
}