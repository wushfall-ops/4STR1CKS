#version 330

layout(std140) uniform Globals {
    ivec3 CameraBlockPos;
    vec3 CameraOffset;
    vec2 ScreenSize;
    float GlintAlpha;
    float GameTime;
    int MenuBlurRadius;
    int UseRgss;
};

layout(std140) uniform Uniforms {
    vec4 uScreen;
    vec4 uColor;
    vec4 uParams;
    vec4 uShaderParams;
    vec4 uCameraRight;
    vec4 uCameraUp;
    vec4 uCameraForward;
};

in vec2 vUV;
out vec4 fragColor;

float hash(vec2 p) {
    p = fract(p * vec2(123.34, 345.45));
    p += dot(p, p + 34.345);
    return fract(p.x * p.y);
}

float noise(vec2 p) {
    vec2 i = floor(p);
    vec2 f = fract(p);
    f = f * f * (3.0 - 2.0 * f);
    return mix(
        mix(hash(i), hash(i + vec2(1.0, 0.0)), f.x),
        mix(hash(i + vec2(0.0, 1.0)), hash(i + vec2(1.0, 1.0)), f.x),
        f.y
    );
}

float fbm(vec2 p) {
    float v = 0.0;
    float a = 0.5;
    for (int i = 0; i < 5; i++) {
        v += noise(p) * a;
        p = p * 2.02 + vec2(8.4, 5.7);
        a *= 0.5;
    }
    return v;
}

// Космос: тёмный фон + мягкая туманность + мерцающие звёзды. Координаты от направления взгляда.
vec3 cosmosColor(vec2 sky, float t, vec3 base) {
    // фоновая туманность — колышется НА МЕСТЕ (осцилляция домена, без линейного сноса)
    vec2 sc = sky * 3.0;
    sc += 0.18 * vec2(sin(sc.y * 1.3 + t * 5.0), cos(sc.x * 1.3 + t * 4.0));
    float neb = smoothstep(0.35, 0.92, fbm(sc * 0.8));
    vec3 nebCol = mix(base * 0.6, mix(base, vec3(1.0), 0.45), neb);

    // звёзды (сетка ячеек 3x3 — без разрывов на границах)
    vec2 grid = sky * 60.0;
    vec2 cell = floor(grid);
    vec2 fp = fract(grid);
    float star = 0.0;
    for (int gy = -1; gy <= 1; gy++) {
        for (int gx = -1; gx <= 1; gx++) {
            vec2 c = cell + vec2(gx, gy);
            float h = hash(c);
            if (h > 0.90) {
                vec2 sp = vec2(hash(c + 0.13), hash(c + 0.27));
                float d = length(fp - vec2(gx, gy) - sp);
                float bright = (h - 0.90) / 0.10;
                float tw = 0.6 + 0.4 * sin(t * 8.0 + h * 40.0);
                star += smoothstep(0.055, 0.0, d) * bright * tw;
            }
        }
    }
    star = clamp(star, 0.0, 1.0);

    vec3 col = base * 0.06;
    col += nebCol * neb * 0.55;
    col += vec3(1.0) * star;
    col += mix(base, vec3(1.0), 0.6) * star * 0.4;
    return col;
}

float ridged(vec2 p) {
    float v = 0.0;
    float a = 0.55;
    for (int i = 0; i < 4; i++) {
        float r = 1.0 - abs(noise(p) * 2.0 - 1.0);
        v += r * a;
        p = p * 2.18 + vec2(3.1, 9.2);
        a *= 0.52;
    }
    return v;
}

float bwColormapRed(float x) {
    if (x < 0.0) return 54.0 / 255.0;
    if (x < 20049.0 / 82979.0) return (829.79 * x + 54.51) / 255.0;
    return 1.0;
}

float bwColormapGreen(float x) {
    if (x < 20049.0 / 82979.0) return 0.0;
    if (x < 327013.0 / 810990.0) return (8546482679670.0 / 10875673217.0 * x - 2064961390770.0 / 10875673217.0) / 255.0;
    if (x <= 1.0) return (103806720.0 / 483977.0 * x + 19607415.0 / 483977.0) / 255.0;
    return 1.0;
}

float bwColormapBlue(float x) {
    if (x < 0.0) return 54.0 / 255.0;
    if (x < 7249.0 / 82979.0) return (829.79 * x + 54.51) / 255.0;
    if (x < 20049.0 / 82979.0) return 127.0 / 255.0;
    if (x < 327013.0 / 810990.0) return (792.0224934136139 * x - 64.36479073560233) / 255.0;
    return 1.0;
}

vec3 bwColormap(float x) {
    return vec3(bwColormapRed(x), bwColormapGreen(x), bwColormapBlue(x));
}

float bwRand(vec2 n) {
    return fract(sin(dot(n, vec2(12.9898, 4.1414))) * 43758.5453);
}

float bwNoise(vec2 p) {
    vec2 ip = floor(p);
    vec2 u = fract(p);
    u = u * u * (3.0 - 2.0 * u);
    float res = mix(
        mix(bwRand(ip), bwRand(ip + vec2(1.0, 0.0)), u.x),
        mix(bwRand(ip + vec2(0.0, 1.0)), bwRand(ip + vec2(1.0, 1.0)), u.x),
        u.y
    );
    return res * res;
}

const mat2 BW_MTX = mat2(0.80, 0.60, -0.60, 0.80);

float bwFbm(vec2 p, float t) {
    float f = 0.0;
    f += 0.500000 * bwNoise(p + vec2(t)); p = BW_MTX * p * 2.02;
    f += 0.031250 * bwNoise(p); p = BW_MTX * p * 2.01;
    f += 0.250000 * bwNoise(p); p = BW_MTX * p * 2.03;
    f += 0.125000 * bwNoise(p); p = BW_MTX * p * 2.01;
    f += 0.062500 * bwNoise(p); p = BW_MTX * p * 2.04;
    f += 0.015625 * bwNoise(p + vec2(sin(t)));
    return f / 0.96875;
}

float bwPattern(vec2 p, float t) {
    return bwFbm(p + vec2(bwFbm(p + vec2(bwFbm(p, t)), t)), t);
}

// ── Закат: тёплый горизонт, тяжёлые низкие облака, предгрозовая хмарь ──
vec3 sunsetColor(vec3 ray, vec2 skyUv, float t, vec3 base) {
    vec3 dir = normalize(ray);
    float h = clamp(dir.y, -1.0, 1.0);

    // Солнце низко над горизонтом, направление закреплено в мире.
    vec3 sunDir = normalize(vec3(0.86, 0.07, -0.50));
    float sunDot = max(dot(dir, sunDir), 0.0);

    // Палитра: от раскалённого горизонта к густым сумеркам в зените.
    vec3 cHorizon = vec3(1.00, 0.54, 0.24);
    vec3 cLow     = vec3(0.82, 0.33, 0.36);
    vec3 cMid     = vec3(0.34, 0.27, 0.47);
    vec3 cHigh    = vec3(0.09, 0.12, 0.25);

    vec3 sky = mix(cHorizon, cLow, smoothstep(-0.08, 0.18, h));
    sky = mix(sky, cMid, smoothstep(0.12, 0.46, h));
    sky = mix(sky, cHigh, smoothstep(0.36, 0.88, h));

    // Ореол вокруг солнца и сам диск, приглушённый облачной дымкой.
    float glow = pow(sunDot, 6.0) * 0.85 + pow(sunDot, 48.0) * 1.30;
    sky += vec3(1.00, 0.63, 0.31) * glow;
    float disc = smoothstep(0.9986, 0.9996, sunDot);
    sky = mix(sky, vec3(1.00, 0.86, 0.64), disc * 0.80);

    // Облака: два слоя, узор закреплён в мире, движение — лёгкое колыхание на месте.
    vec2 cu = skyUv * 1.6;
    cu += 0.10 * vec2(sin(cu.y * 1.1 + t * 0.25), cos(cu.x * 1.0 + t * 0.21));
    float n1 = fbm(cu * 0.90);
    float n2 = fbm(cu * 2.10 + vec2(3.1, 1.7));
    float cover = n1 * 0.65 + n2 * 0.35;

    // Перед дождём облака тяжёлые и висят низко — у горизонта их заметно больше.
    float lowBand = 1.0 - smoothstep(-0.02, 0.64, h);   // перевёрнутый smoothstep по спеке undefined
    float density = smoothstep(0.44 - lowBand * 0.18, 0.80, cover);

    // Подсветка кромок со стороны солнца — тот самый золотой контур.
    float rim = clamp(fbm(cu * 0.90 + sunDir.xz * 0.35) - n1, 0.0, 1.0) * 3.0;
    vec3 cloudDark = vec3(0.12, 0.12, 0.17);
    vec3 cloudLit  = mix(vec3(0.52, 0.43, 0.50), vec3(1.00, 0.70, 0.42), pow(sunDot, 1.6));
    vec3 cloud = mix(cloudDark, cloudLit, clamp(rim * 0.70 + pow(sunDot, 3.0) * 0.50, 0.0, 1.0));
    sky = mix(sky, cloud, density * 0.92);

    // Хмарь у горизонта: воздух перед дождём тяжелеет и сереет.
    float haze = 1.0 - smoothstep(-0.16, 0.36, h);
    sky = mix(sky, vec3(0.27, 0.26, 0.30), haze * 0.34);

    // Косые полосы дождя вдали — только там, где висит облачный фронт.
    float streak = pow(clamp(1.0 - abs(sin((skyUv.x * 3.0 + skyUv.y * 9.0) * 6.0 + t * 2.2)), 0.0, 1.0), 22.0);
    sky = mix(sky, vec3(0.42, 0.44, 0.50), streak * haze * density * 0.20);

    sky *= 0.92;                                   // общая приглушённость перед грозой
    return mix(sky, sky * (0.65 + base * 0.70), 0.18);  // лёгкая подкраска темой клиента
}

void main() {
    vec2 uv = vUV;
    vec2 aspect = vec2(uScreen.x / max(uScreen.y, 1.0), 1.0);
    vec2 rayUv = (uv * 2.0 - 1.0) * aspect;
    vec3 ray = normalize(uCameraRight.xyz * rayUv.x + uCameraUp.xyz * rayUv.y + uCameraForward.xyz * 1.25);
    vec2 skyUv = ray.xz * (1.0 - abs(ray.y) * 0.35) + vec2(ray.y * 0.22, ray.y * -0.18);
    vec3 base = uColor.rgb;
    float alpha = clamp(uParams.x, 0.0, 1.0);
    float time = (GameTime * 1200.0 + uParams.w) * max(uParams.y, 0.01);
    int shaderMode = int(uShaderParams.x);
    float horizon = smoothstep(-0.45, 0.85, ray.y);
    vec3 color = mix(base * 0.16, base * 0.48, horizon);

    // анимация всех режимов сделана "на месте": вместо линейного time (сноса узора по небу)
    // — осцилляция (sin/cos), поэтому эффект живёт, но небо закреплено в мире.
    if (shaderMode == 0) {
        float pulse = 0.72 + 0.28 * sin((skyUv.x + skyUv.y) * 8.0 + sin(time * 1.2) * 2.5);
        float mist = fbm(skyUv * 3.0 + 0.25 * vec2(sin(time * 0.5), cos(time * 0.4)));
        color = mix(color, mix(base, vec3(1.0), 0.35), mist * pulse);
    } else if (shaderMode == 2) {
        vec2 flow = skyUv * 2.5;
        vec2 drift = 0.7 * vec2(sin(time * 0.35), cos(time * 0.28));
        vec2 warp = vec2(fbm(flow * 0.90 + drift * 0.75 + vec2(0.0, 4.1)), fbm(flow * 0.78 - drift * 0.48 + vec2(3.7, 1.8)));
        vec2 q = flow + (warp - 0.5) * 1.8;
        float mist = fbm(q * 0.72 - drift * 0.24 + vec2(4.2, 8.1));
        float veins = pow(clamp(ridged(q * 1.85 + vec2(mist * 2.5, mist * 1.6) - drift * 0.55), 0.0, 1.0), 2.4);
        float strands = pow(clamp(1.0 - abs(sin((q.x * 1.08 + q.y * 0.42) * 1.7 + sin(time * 0.7) * 2.5 + mist * 4.3)), 0.0, 1.0), 4.8);
        float energy = clamp(mist * 0.22 + veins * 0.88 + strands * 0.55, 0.0, 1.0);
        color = mix(color, mix(base, vec3(1.0), 0.45), energy);
    } else if (shaderMode == 4) {
        float p1 = sin(skyUv.x * 8.0 + sin(time * 1.2) * 2.5);
        float p2 = sin(skyUv.y * 6.0 + cos(time * 0.9) * 2.5);
        float p3 = sin((skyUv.x + skyUv.y) * 5.0 + sin(time * 1.0) * 2.5);
        float n = (p1 + p2 + p3) * 0.16 + 0.5;
        color = mix(base * (0.28 + horizon * 0.25), mix(base, vec3(1.0), 0.7), n);
    } else if (shaderMode == 6) {
        float densityMix = 0.25;
        vec2 flow = skyUv * 1.35;
        vec2 drift = 0.7 * vec2(sin(time * 0.33), cos(time * 0.26));
        vec2 warp = vec2(fbm(flow * 0.85 + drift * 0.55 + vec2(0.0, 4.1)), fbm(flow * 0.80 - drift * 0.42 + vec2(3.7, 1.8)));
        vec2 q = flow + (warp - 0.5) * mix(1.3, 2.5, densityMix);
        float mist = fbm(q * 0.70 - drift * 0.18 + vec2(4.2, 8.1));
        float band1 = 1.0 - abs(sin((q.x * 1.02 + q.y * 0.38 + sin(time * 0.4) * 1.6) * 1.85 + mist * 4.8));
        float band2 = 1.0 - abs(sin((q.x * -0.58 + q.y * 1.10 - cos(time * 0.3) * 1.6) * 1.45 - mist * 3.2));
        band1 = pow(clamp(band1, 0.0, 1.0), 4.2);
        band2 = pow(clamp(band2, 0.0, 1.0), 4.8);
        float veins = pow(clamp(ridged(q * 1.90 + vec2(mist * 2.7, mist * 1.9) - drift * 0.55), 0.0, 1.0), 2.4);
        float energy = clamp(mist * 0.24 + band1 * 0.70 + band2 * 0.40 + veins * 0.84, 0.0, 1.0);
        color = base * (0.18 + horizon * 0.18 + smoothstep(0.16, 0.98, energy) * 1.1);
    } else if (shaderMode == 8) {
        float shade = bwPattern(skyUv * 2.15, sin(time * 0.25) * 3.0);
        color = mix(bwColormap(shade), base, 0.22);
    } else if (shaderMode == 10) {
        vec2 waveUv = skyUv * 2.0;
        float wt = sin(time * 0.5) * 3.0;
        for (float i = 1.0; i < 10.0; i++) {
            waveUv.x += 0.6 / i * cos(i * 2.5 * waveUv.y + wt);
            waveUv.y += 0.6 / i * cos(i * 1.5 * waveUv.x + wt);
        }
        float wave = 0.1 / max(abs(sin(wt - waveUv.y - waveUv.x)), 0.08);
        color = mix(base * (0.12 + horizon * 0.22), base, clamp(wave, 0.0, 1.8));
    } else if (shaderMode == 12) {
        // Test — драматичная туманность. Узор закреплён в мире (skyUv), анимация НА МЕСТЕ:
        // домен колышется/пульсирует, но не сползает по небу.
        vec2 q = skyUv * 2.6;
        q += 0.18 * vec2(sin(q.y * 1.4 + time * 0.6), cos(q.x * 1.4 + time * 0.5)); // колыхание на месте

        float n1 = fbm(q * 0.75 + vec2(4.2, 8.1));
        float n2 = fbm(q * 1.6 + vec2(1.3, 2.7));
        float clouds = smoothstep(0.30, 0.85, n1 * 0.7 + n2 * 0.3);

        // переливчатость оттенка из темы (без хардкод-цветов)
        float hue = 0.5 + 0.5 * sin(q.x * 0.5 + q.y * 0.4 + time * 0.3);
        vec3 tone2  = clamp(base.gbr * 1.08 + 0.04, 0.0, 1.0);
        vec3 nebCol = mix(base, tone2, hue);
        vec3 glow   = mix(base, vec3(1.0), 0.7);

        vec3 neb = mix(base * 0.22, nebCol * 1.15, clouds);   // насыщенные средние тона
        float core = pow(clouds, 2.2);                        // яркие ядра -> в белый
        neb = mix(neb, glow, core * 0.85);
        neb += glow * core * 0.5;
        float rim = clouds * (1.0 - clouds) * 4.0;            // свечение кромок
        neb += glow * rim * 0.15;

        color = mix(color, neb, clouds * 0.9 + 0.1);
    } else if (shaderMode == 14) {
        // Cosmos — звёздное небо + мягкая туманность (всё от направления взгляда).
        color = mix(color, cosmosColor(skyUv, time * 0.08, base), 0.92);
    } else if (shaderMode == 16) {
        // Galaxy — космос по всему небу + одна яркая зона в стиле Full (как ядро галактики).
        vec3 cosmos = cosmosColor(skyUv, time * 0.08, base);

        // зона Full вокруг фиксированного направления в мире (видна выше горизонта)
        vec3 focus = normalize(vec3(0.45, 0.6, -0.55));
        float region = smoothstep(0.45, 0.9, dot(normalize(ray), focus));

        float pulse = 0.72 + 0.28 * sin(time * 1.5 + (skyUv.x + skyUv.y) * 8.0);
        // туманность зоны колышется на месте (без сноса)
        float mist = fbm(skyUv * 3.0 + 0.2 * vec2(sin(skyUv.y * 2.0 + time * 0.6), cos(skyUv.x * 2.0 + time * 0.5)));
        vec3 full = mix(cosmos, mix(base, vec3(1.0), 0.5), mist * pulse);

        color = mix(color, mix(cosmos, full, region), 0.92);
    } else if (shaderMode == 18) {
        // Закат — своя палитра, тема подмешивается лишь слегка.
        color = sunsetColor(ray, skyUv, time, base);
    }

    float zenith = smoothstep(-0.15, 0.85, ray.y);
    // У заката собственная светотень по высоте — общий затемнитель её бы съел.
    if (shaderMode != 18) {
        color *= 0.78 + zenith * 0.28;
    }
    fragColor = vec4(color, alpha);
}
