package pl.nask.crs.app.authorization.aspects;

import org.aspectj.lang.JoinPoint;

public final class PermissionAspectHelper {
	private PermissionAspectHelper(){}

	public static String makePermissionName(JoinPoint joinPoint) {
		String typeName = joinPoint.getSignature().getDeclaringTypeName();
		String methodName = joinPoint.getSignature().getName();
		String permissionName = typeName + "." + methodName;
		return permissionName;
	};

	
}
