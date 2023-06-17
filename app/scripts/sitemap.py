from pathlib import PurePosixPath, Path

def init():
    routes = ''
    root = Path('src/routes')
    for name in root.rglob('+page.svelte'):
        name = PurePosixPath(name).parent
        route = str(PurePosixPath(name).relative_to(root))
        route = route.replace('.', '')
        for ele in ['(public)', '']:
            route = route.replace(ele, '')
        if not route.startswith('/'):
            route = '/' + route

        print(route)

        routes += f"""
        <url>
            <loc>https://urosjarc.si{route}</loc>
        </url>
        """


    return f'''
    <urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd">
    {routes}
    </urlset>
    '''

with open('static/sitemap.xml', 'w') as f:
    f.write(init())
