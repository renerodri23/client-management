import { createSlice, type PayloadAction } from '@reduxjs/toolkit';

interface AuthState {
  token: string | null;
  userEmail: string | null;
  role: string | null;
  uuid: string | null;
  isAuthenticated: boolean;
}

const initialState: AuthState = {
  token: localStorage.getItem('token'),
  userEmail: localStorage.getItem('userEmail'),
  role: localStorage.getItem('role'),
  uuid: localStorage.getItem('uuid'),
  isAuthenticated: !!localStorage.getItem('token'),
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    setCredentials: (state, action: PayloadAction<{ token: string; email: string; role: string; userId: string }>) => {
      state.token = action.payload.token;
      state.userEmail = action.payload.email;
      state.role = action.payload.role;
      state.uuid = action.payload.userId;
      state.isAuthenticated = true;
      localStorage.setItem('token', action.payload.token);
      localStorage.setItem('userEmail', action.payload.email);
      localStorage.setItem('role', action.payload.role);
      localStorage.setItem('uuid', action.payload.userId);
    },
    logout: (state) => {
      state.token = null;
      state.userEmail = null;
      state.role = null;
      state.uuid = null;
      state.isAuthenticated = false;
      localStorage.clear();
    },
  },
});

export const { setCredentials, logout } = authSlice.actions;
export default authSlice.reducer;