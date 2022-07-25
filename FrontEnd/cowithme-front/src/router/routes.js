const routes = [
	{
		path: '/',
		component: () => import('layouts/MainLayout.vue'),
		children: [{ path: '', component: () => import('pages/IndexPage.vue') }],
	},

	// Always leave this as last one,
	// but you can also remove it
	{
		path: '/teacherHome',
		component: () => import('pages/TeacherHomePage.vue'),
	},
	{
		path: '/:catchAll(.*)*',
		component: () => import('pages/ErrorNotFound.vue'),
	},
	// 로그인/회원가입
	{
		path: '/login',
		component: () => import('pages/LoginPage.vue'),
	},
	{
		path: '/IDfind',
		component: () => import('pages/IDFindPage.vue'),
	},
	{
		path: '/PWfind',
		component: () => import('pages/PWFindPage.vue'),
	},
	{
		path: '/join',
		component: () => import('pages/SignUpPage.vue'),
	},
	// home
	{
		path: '/home',
		component: () => import('pages/StudentHomePage.vue'),
	},
];

export default routes;
