varying lowp vec2 f_tex; 
#ifdef VERTEX
attribute lowp vec4  v_pos; 
attribute lowp vec2   v_tex;
uniform lowp mat4 combined; 
	 void main(){ 
		f_tex =   v_tex;
		gl_Position =  combined *   v_pos; 
	}
#endif

#ifdef FRAGMENT 
	 uniform lowp sampler2D u_texture0;
void main() {
   lowp vec4 texval1 = texture2D(u_texture0, f_tex);
   gl_FragColor = texval1;
   
}
#endif
