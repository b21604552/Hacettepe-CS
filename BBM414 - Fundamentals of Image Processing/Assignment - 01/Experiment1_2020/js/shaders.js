class ShaderUtil{
    static domShaderSrc(elmID){
        let elm = document.getElementById(elmID);
        if(!elm || elm.text === ""){
            console.log(elmID + " shader not found or no text.");
            return null;
        }
        return elm.text;
    }

    static createShader(src,type){
        let shader = gl.createShader(type);
        gl.shaderSource(shader,src);
        gl.compileShader(shader);

        if(!gl.getShaderParameter(shader, gl.COMPILE_STATUS)){
            console.error("Error compiling shader : " + src, gl.getShaderInfoLog(shader));
            gl.deleteShader(shader);
            return null;
        }
        return shader;
    }

    static createProgram(vertex_shader,fragment_shader){
        let program = gl.createProgram();
        gl.attachShader(program, vertex_shader);
        gl.attachShader(program, fragment_shader);
        gl.linkProgram(program);
        if(!gl.getProgramParameter(program, gl.LINK_STATUS) ) {
            let info = gl.getProgramInfoLog(program);
            console.error("Could not link WebGL2 program :" + info);
            return;
        }
        return program;
    }

    static buffer(shape, shape_color) {
        const buffer = gl.createBuffer();
        gl.bindBuffer(gl.ARRAY_BUFFER, buffer);
        gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(shape.concat(shape_color)), gl.STATIC_DRAW);
        gl.bufferSubData(gl.ARRAY_BUFFER, 0, new Float32Array(shape));
        gl.bufferSubData(gl.ARRAY_BUFFER, shape.length *4, new Float32Array(shape_color));
        return buffer;
    }

    static drawShape(shape, shape_buffer, shape_mode, vertex_number) {
        gl.bindBuffer(gl.ARRAY_BUFFER, shape_buffer);
        gl.vertexAttribPointer(vertexLocation, 2, gl.FLOAT, false, 0, 0);
        gl.enableVertexAttribArray(vertexLocation);
        gl.enableVertexAttribArray(colorLocation);
        gl.vertexAttribPointer(colorLocation, 3, gl.FLOAT, false, 0, shape.length * 4);
        gl.drawArrays(shape_mode, 0, vertex_number);
    }

    static clear() {
        gl.clearColor(1.0, 1.0, 1.0, 1.0);
        gl.clear(gl.COLOR_BUFFER_BIT);
    }

    static getConcatData(radius, centers, j, i=0) {
        return [radius * Math.cos(j * radian) + centers[i][0], radius * Math.sin(j * radian) + centers[i][1]]
    }

    static createYellowCircleArray() {
        let circleArray = [[]];
        for (let j = 0; j < 360; j++) {
            circleArray[0] = circleArray[0].concat(ShaderUtil.getConcatData(0.60, [[0.0, 0.0]], j));
        }
        return circleArray
    }

    static createBlackCircleArray() {
        let circleArray=[[],[]];
        for(let i=0;i<2;i++){
            for (let j = 0; j < 360; j++) {
                circleArray[i] = circleArray[i].concat(ShaderUtil.getConcatData(0.10, [[0.20,0.20],[-0.20,0.20]], j, i));
            }
        }
        return circleArray;
    }

    static createColorArray(color, loop){
        let colorArray = [];
        for(let i = 0; i < loop; i++) {
            colorArray = colorArray.concat(color);
        }
        return colorArray;
    }

    static quadraticBezier(p0, p1, p2, t) {
        let x = Math.pow(1 - t, 2) * p0.x + (1 - t) * 2 * t * p1.x + t * t * p2.x,
            y = Math.pow(1 - t, 2) * p0.y + (1 - t) * 2 * t * p1.y + t * t * p2.y;
        return [x,y];
    }

    static getCurvePositions(p0,p1,p2){
        let curvePositions = []
        for(let t = 0 ; t < 1; t = t + 0.0005 ){
            curvePositions = curvePositions.concat(ShaderUtil.quadraticBezier(p0,p1,p2,t));
        }
        return curvePositions;
    }

    static drawFace(){
        let yellowCircleArray = ShaderUtil.createYellowCircleArray(),

            yellowCircle = ShaderUtil.createColorArray([0.929,0.831,0.09],360),
            yellowCircleBuffers = [];
        yellowCircleBuffers = yellowCircleBuffers.concat(ShaderUtil.buffer(yellowCircleArray[0], yellowCircle));

        ShaderUtil.drawShape(yellowCircleArray[0], yellowCircleBuffers[0], gl.TRIANGLE_FAN, 360);
    }

    static drawEyes(){
        let blackCircleArray = ShaderUtil.createBlackCircleArray(),
            blackCircle = ShaderUtil.createColorArray([0.231,0.212,0.078],360),
            blackCircleBuffers = [];

        for (let i = 0; i < 2; i++){
            blackCircleBuffers = blackCircleBuffers.concat(ShaderUtil.buffer(blackCircleArray[i],blackCircle));
        }

        for (let i = 0; i < 2; i++){
            ShaderUtil.drawShape(blackCircleArray[i],blackCircleBuffers[i],gl.TRIANGLE_FAN,360);
        }
    }

    static drawMask(){
        let squareColorArray = ShaderUtil.createColorArray([0.839,0.878,0.922],4),
            squareCornerColorArray = ShaderUtil.createColorArray([0.839,0.878,0.922],4),
            squareCornersBuffers = [],
            squareBuffers;

        for (let i = 0; i < 4; i++){
            squareCornersBuffers = squareCornersBuffers.concat(ShaderUtil.buffer(squareCorners[i],squareCornerColorArray));
        }

        squareBuffers = ShaderUtil.buffer(square,squareColorArray);

        ShaderUtil.drawShape(square,squareBuffers,gl.TRIANGLE_STRIP,4);
        ShaderUtil.drawMaskCorners(squareCornersBuffers,4);
    }

    static drawMaskCorners(buffer,loop){
        for (let i = 0; i < loop; i++){
            ShaderUtil.drawShape(squareCorners[i],buffer[i],gl.TRIANGLE_STRIP,4);
        }
    }

    static drawCurves(){
        let curveColorArray = ShaderUtil.createColorArray([0.839,0.878,0.922],2000),
            curveTopPositionsBuffer = [],
            curveBottomPositionsBuffer = [],
            curveTopPositions,
            curveBottomPositions;

        curveTopPositions = ShaderUtil.getCurvePositions(curves.top.p0,curves.top.p1,curves.top.p2);
        curveBottomPositions = ShaderUtil.getCurvePositions(curves.bottom.p0,curves.bottom.p1,curves.bottom.p2);
        curveTopPositionsBuffer = curveTopPositionsBuffer.concat(ShaderUtil.buffer(curveTopPositions,curveColorArray));
        curveBottomPositionsBuffer = curveBottomPositionsBuffer.concat(ShaderUtil.buffer(curveBottomPositions,curveColorArray));

        ShaderUtil.drawShape(curveTopPositions,curveTopPositionsBuffer[0],gl.TRIANGLE_FAN,2000);
        ShaderUtil.drawShape(curveBottomPositions,curveBottomPositionsBuffer[0],gl.TRIANGLE_FAN,2000);
    }
}