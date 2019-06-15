varying vec2 f_tex; 
#ifdef VERTEX
attribute vec4  v_pos; 
attribute vec2   v_tex;
uniform mat4 combined; 
	 void main(){ 
		f_tex =   v_tex;
		gl_Position =  combined *   v_pos; 
	}
#endif

#ifdef FRAGMENT
#ifdef GL_ES 
#define LOWP lowp 
precision mediump float; 
#else 
#define LOWP  
#endif 
	 uniform sampler2D u_texture0;
void main() {
   vec4 texval1 = texture2D(u_texture0, f_tex);
   gl_FragColor = texval1;
   
}
#endif
