interface RestrictedRouteProps {
  condition: () => boolean;
  component: JSX.Element;
  invalidComponent: JSX.Element;
}

export default function RestrictedRoute(props: RestrictedRouteProps) {
  if (props.condition()) {
    return props.component;
  } else {
    return props.invalidComponent;
  }
}
