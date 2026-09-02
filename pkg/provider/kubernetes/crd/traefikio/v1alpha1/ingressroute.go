package v1alpha1

import (
	"github.com/traefik/traefik/v3/pkg/config/dynamic"
	"github.com/traefik/traefik/v3/pkg/types"
	metav1 "k8s.io/apimachinery/pkg/apis/meta/v1"
	"k8s.io/apimachinery/pkg/util/intstr"
)

// IngressRouteSpec defines the desired state of IngressRoute.
type IngressRouteSpec struct {
	// Routes defines the list of routes.
	Routes []Route `json:"routes"`
	EntryPoints []string `json:"entryPoints,omitempty"`
	TLS *TLS `json:"tls,omitempty"`
	// EntryPoints defines the list of entry point names to bind to.
	// Entry points have to be configured in the static configuration.
	// More info: https://doc.traefik.io/traefik/v3.4/routing/entrypoints/
	// Default: all.
	// TLS defines the TLS configuration.
	// More info: https://doc.traefik.io/traefik/v3.4/routing/routers/#tls
}

// Route holds the HTTP route configuration.
type Route struct {
	// Match defines the router's rule.
	// More info: https://doc.traefik.io/traefik/v3.4/routing/routers/#rule
	Match string `json:"match"`
	Kind string `json:"kind,omitempty"`
	Priority int `json:"priority,omitempty"`
	Syntax string `json:"syntax,omitempty"`
	Services []Service `json:"services,omitempty"`
	Middlewares []MiddlewareRef `json:"middlewares,omitempty"`
	Observability *dynamic.RouterObservabilityConfig `json:"observability,omitempty"`
	// Kind defines the kind of the route.
	// Rule is the only supported kind.
	// If not defined, defaults to Rule.
	// +kubebuilder:validation:Enum=Rule
	// Priority defines the router's priority.
	// More info: https://doc.traefik.io/traefik/v3.4/routing/routers/#priority
	// +kubebuilder:validation:Maximum=9223372036854774807
	// Syntax defines the router's rule syntax.
	// More info: https://doc.traefik.io/traefik/v3.4/routing/routers/#rulesyntax
	// Deprecated: Please do not use this field and rewrite the router rules to use the v3 syntax.
	// Services defines the list of Service.
	// It can contain any combination of TraefikService and/or reference to a Kubernetes Service.
	// Middlewares defines the list of references to Middleware resources.
	// More info: https://doc.traefik.io/traefik/v3.4/routing/providers/kubernetes-crd/#kind-middleware
	// Observability defines the observability configuration for a router.
	// More info: https://doc.traefik.io/traefik/v3.2/routing/routers/#observability
}

// TLS holds the TLS configuration.
// More info: https://doc.traefik.io/traefik/v3.4/routing/routers/#tls
type TLS struct {
	// SecretName is the name of the referenced Kubernetes Secret to specify the certificate details.
	SecretName string `json:"secretName,omitempty"`
	Options *TLSOptionRef `json:"options,omitempty"`
	Store *TLSStoreRef `json:"store,omitempty"`
	CertResolver string `json:"certResolver,omitempty"`
	Domains []types.Domain `json:"domains,omitempty"`
	// Options defines the reference to a TLSOption, that specifies the parameters of the TLS connection.
	// If not defined, the `default` TLSOption is used.
	// More info: https://doc.traefik.io/traefik/v3.4/https/tls/#tls-options
	// Store defines the reference to the TLSStore, that will be used to store certificates.
	// Please note that only `default` TLSStore can be used.
	// CertResolver defines the name of the certificate resolver to use.
	// Cert resolvers have to be configured in the static configuration.
	// More info: https://doc.traefik.io/traefik/v3.4/https/acme/#certificate-resolvers
	// Domains defines the list of domains that will be used to issue certificates.
	// More info: https://doc.traefik.io/traefik/v3.4/routing/routers/#domains
}

// TLSOptionRef is a reference to a TLSOption resource.
type TLSOptionRef struct {
	// Name defines the name of the referenced TLSOption.
	// More info: https://doc.traefik.io/traefik/v3.4/routing/providers/kubernetes-crd/#kind-tlsoption
	Name string `json:"name"`
	Namespace string `json:"namespace,omitempty"`
	// Namespace defines the namespace of the referenced TLSOption.
	// More info: https://doc.traefik.io/traefik/v3.4/routing/providers/kubernetes-crd/#kind-tlsoption
}

// TLSStoreRef is a reference to a TLSStore resource.
type TLSStoreRef struct {
	// Name defines the name of the referenced TLSStore.
	// More info: https://doc.traefik.io/traefik/v3.4/routing/providers/kubernetes-crd/#kind-tlsstore
	Name string `json:"name"`
	Namespace string `json:"namespace,omitempty"`
	// Namespace defines the namespace of the referenced TLSStore.
	// More info: https://doc.traefik.io/traefik/v3.4/routing/providers/kubernetes-crd/#kind-tlsstore
}

// LoadBalancerSpec defines the desired state of LoadBalancer.
// It can reference either a Kubernetes Service object (a load-balancer of servers),
// or a TraefikService object (a load-balancer of Traefik services).
type LoadBalancerSpec struct {
	// Name defines the name of the referenced Kubernetes Service or TraefikService.
	// The differentiation between the two is specified in the Kind field.
	Name string `json:"name"`
	Kind string `json:"kind,omitempty"`
	Namespace string `json:"namespace,omitempty"`
	Sticky *dynamic.Sticky `json:"sticky,omitempty"`
	Port intstr.IntOrString `json:"port,omitempty"`
	Scheme string `json:"scheme,omitempty"`
	Strategy dynamic.BalancerStrategy `json:"strategy,omitempty"`
	PassHostHeader *bool `json:"passHostHeader,omitempty"`
	ResponseForwarding *ResponseForwarding `json:"responseForwarding,omitempty"`
	ServersTransport string `json:"serversTransport,omitempty"`
	Weight *int `json:"weight,omitempty"`
	NativeLB *bool `json:"nativeLB,omitempty"`
	NodePortLB bool `json:"nodePortLB,omitempty"`
	HealthCheck *ServerHealthCheck `json:"healthCheck,omitempty"`
	// Kind defines the kind of the Service.
	// +kubebuilder:validation:Enum=Service;TraefikService
	// Namespace defines the namespace of the referenced Kubernetes Service or TraefikService.
	// Sticky defines the sticky sessions configuration.
	// More info: https://doc.traefik.io/traefik/v3.4/routing/services/#sticky-sessions
	// Port defines the port of a Kubernetes Service.
	// This can be a reference to a named port.
	// +kubebuilder:validation:XIntOrString
	// Scheme defines the scheme to use for the request to the upstream Kubernetes Service.
	// It defaults to https when Kubernetes Service port is 443, http otherwise.
	// Strategy defines the load balancing strategy between the servers.
	// Supported values are: wrr (Weighed round-robin) and p2c (Power of two choices).
	// RoundRobin value is deprecated and supported for backward compatibility.
	// TODO: when the deprecated RoundRobin value will be removed, set the default value to wrr.
	// +kubebuilder:validation:Enum=wrr;p2c;RoundRobin
	// PassHostHeader defines whether the client Host header is forwarded to the upstream Kubernetes Service.
	// By default, passHostHeader is true.
	// ResponseForwarding defines how Traefik forwards the response from the upstream Kubernetes Service to the client.
	// ServersTransport defines the name of ServersTransport resource to use.
	// It allows to configure the transport between Traefik and your servers.
	// Can only be used on a Kubernetes Service.
	// Weight defines the weight and should only be specified when Name references a TraefikService object
	// (and to be precise, one that embeds a Weighted Round Robin).
	// +kubebuilder:validation:Minimum=0
	// NativeLB controls, when creating the load-balancer,
	// whether the LB's children are directly the pods IPs or if the only child is the Kubernetes Service clusterIP.
	// The Kubernetes Service itself does load-balance to the pods.
	// By default, NativeLB is false.
	// NodePortLB controls, when creating the load-balancer,
	// whether the LB's children are directly the nodes internal IPs using the nodePort when the service type is NodePort.
	// It allows services to be reachable when Traefik runs externally from the Kubernetes cluster but within the same network of the nodes.
	// By default, NodePortLB is false.
	// Healthcheck defines health checks for ExternalName services.
}

type ResponseForwarding struct {
	// FlushInterval defines the interval, in milliseconds, in between flushes to the client while copying the response body.
	// A negative value means to flush immediately after each write to the client.
	// This configuration is ignored when ReverseProxy recognizes a response as a streaming response;
	// for such responses, writes are flushed to the client immediately.
	// Default: 100ms
	FlushInterval string `json:"flushInterval,omitempty"`
}

type ServerHealthCheck struct {
	// Scheme replaces the server URL scheme for the health check endpoint.
	Scheme string `json:"scheme,omitempty"`
	Mode string `json:"mode,omitempty"`
	Path string `json:"path,omitempty"`
	Method string `json:"method,omitempty"`
	Status int `json:"status,omitempty"`
	Port int `json:"port,omitempty"`
	Interval *intstr.IntOrString `json:"interval,omitempty"`
	UnhealthyInterval *intstr.IntOrString `json:"unhealthyInterval,omitempty"`
	Timeout *intstr.IntOrString `json:"timeout,omitempty"`
	Hostname string `json:"hostname,omitempty"`
	FollowRedirects *bool `json:"followRedirects,omitempty"`
	Headers map[string]string `json:"headers,omitempty"`
	// Mode defines the health check mode.
	// If defined to grpc, will use the gRPC health check protocol to probe the server.
	// Default: http
	// Path defines the server URL path for the health check endpoint.
	// Method defines the healthcheck method.
	// Status defines the expected HTTP status code of the response to the health check request.
	// Port defines the server URL port for the health check endpoint.
	// Interval defines the frequency of the health check calls for healthy targets.
	// Default: 30s
	// UnhealthyInterval defines the frequency of the health check calls for unhealthy targets.
	// When UnhealthyInterval is not defined, it defaults to the Interval value.
	// Default: 30s
	// Timeout defines the maximum duration Traefik will wait for a health check request before considering the server unhealthy.
	// Default: 5s
	// Hostname defines the value of hostname in the Host header of the health check request.
	// FollowRedirects defines whether redirects should be followed during the health check calls.
	// Default: true
	// Headers defines custom headers to be sent to the health check endpoint.
}

// Service defines an upstream HTTP service to proxy traffic to.
type Service struct {
	LoadBalancerSpec `json:",inline"`
}

// MiddlewareRef is a reference to a Middleware resource.
type MiddlewareRef struct {
	// Name defines the name of the referenced Middleware resource.
	Name string `json:"name"`
	Namespace string `json:"namespace,omitempty"`
	// Namespace defines the namespace of the referenced Middleware resource.
}

// +genclient
// +k8s:deepcopy-gen:interfaces=k8s.io/apimachinery/pkg/runtime.Object
// +kubebuilder:storageversion

// IngressRoute is the CRD implementation of a Traefik HTTP Router.
type IngressRoute struct {
	metav1.TypeMeta `json:",inline"`
	metav1.ObjectMeta `json:"metadata"`

	Spec IngressRouteSpec `json:"spec"`
	// Standard object's metadata.
	// More info: https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata
}

// +k8s:deepcopy-gen:interfaces=k8s.io/apimachinery/pkg/runtime.Object

// IngressRouteList is a collection of IngressRoute.
type IngressRouteList struct {
	metav1.TypeMeta `json:",inline"`
	metav1.ListMeta `json:"metadata"`
	Items []IngressRoute `json:"items"`
	// Standard object's metadata.
	// More info: https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata

	// Items is the list of IngressRoute.
}
